
  const $modifyBtn = document.getElementById('modifyBtn');  //수정버튼
  const $deleteBtn = document.getElementById('deleteBtn');  //삭제버튼
  const $listBtn = document.getElementById('listBtn');      //목록버튼
  const uid = document.getElementById('userId').value;      //유저아이디

  //수정
  $modifyBtn.addEventListener('click',e=>{
    location.href=`/boards/${pid}/edit`; // GET http://서버주소 or 서버도메인/boards/아이디/edit
  });

  //삭제
  $deleteBtn.addEventListener('click',e=>{
    $delModal.showModal();
  });

  //목록
  $listBtn.addEventListener('click',e=>{
    location.href = '/boards';      // GET http://서버주소 or 서버도메인/boards
  }, false);

/* 모달창 ---------------------------------------------*/
  const $delModal = document.getElementById('delModal');    //삭제 모달창
  const $cancelBtn = document.getElementById('cancelBtn');   //취소버튼 모달창
  const $delItemBtn = document.getElementById('delItemBtn'); //삭제버튼 모달창

  $delModal.addEventListener('close',evt=>{
    if($delModal.returnValue == 'ok'){
      location.href = `/boards/${pid}/del`; // GET http://서버주소 or 서버도메인/boards/아이디/del
    }
  });

  //취소버튼 모달창
  $cancelBtn.addEventListener('click', evt=>{
    $delModal.close('cancel');
  });

  //삭제버튼 모달창
  $delItemBtn.addEventListener('click', evt=>{
    $delModal.close('ok');
  });

import {Pagination} from '/js/common.js'

//페이징 객체 생성
const pagination = new Pagination(10, 5); // 한페이지에 보여줄 레코드건수,한페이지에 보여줄 페이지수

// 페이지 로드 시
window.addEventListener('load', () => {
  // 페이지가 로드될 때 댓글 목록을 불러오고 페이징을 표시
  listAndDisplayPagination();
});

// 댓글 목록 조회 및 페이징 표시
function listAndDisplayPagination() {
  // Pagination 객체 생성
  pagination.setTotalRecords(totalCnt); // 총 댓글 수 설정
  pagination.setCurrentPageGroupStart(cpgs); // 페이지 그룹 시작번호 설정
  pagination.setCurrentPage(cp); // 현재 페이지 설정
  pagination.displayPagination(listAndDisplayPagination); // 페이징을 HTML에 표시
}

// 댓글 추가
async function addComment() {
  const url = 'http://localhost:9080/api/boards/comments';
  const currentCnum = document.getElementById('currentCnum').value; // 현재 게시물 번호
  const currentPname = document.getElementById('currentPname').value.slice(0, 30); // 현재 사용자 이름
  //const comment = document.getElementById('comment').value; // 댓글 내용

  const payload = { cnum: currentCnum,
                    contents :comment.value,
                   pname: currentPname };
  console.log(payload);

  const option = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(payload)
  };

  try {
    const res = await fetch(url, option);
    if (!res.ok) throw new Error('서버응답오류');

    const result = await res.json();
    if (result.header.rtcd === '00') {
      console.log(result.body);
      list(); // 목록 다시 불러오기
    } else {
      throw new Error('등록 실패!');
    }
  } catch (err) {
    console.error(err.message);
  }
}


// 댓글 수정
async function updateComment(ccommentId, updatedComment) {
  try {
    const response = await fetch(`/api/boards/comments/${ccommentId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ contents: updatedComment })
    });
    const data = await response.json();

    if (response.ok) {
      return data;
    } else {
      throw new Error(data.message);
    }
  } catch (error) {
    throw new Error('댓글 업데이트 중 오류 발생: ' + error.message);
  }
}

// 댓글 삭제
async function deleteComment(ccommentId) {
  try {
    const response = await fetch(`/api/boards/comments/${ccommentId}`, {
      method: 'DELETE'
    });
    const data = await response.json();

    if (response.ok) {
      return data; // 성공적으로 삭제된 데이터를 반환
    } else {
      throw new Error(data.message); // 오류 메시지 반환
    }
  } catch (error) {
    throw new Error('댓글 삭제 중 오류 발생: ' + error.message);
  }
}


// 목록조회
function list() {
  const reqPage = pagination.currentPage;   // 요청 페이지
  const reqCnt = pagination.recordsPerPage; // 페이지당 레코드수
  const currentCnum = document.getElementById('currentCnum').value; // 현재 게시물 번호
  const $commentList = document.querySelector('.list'); // 댓글 목록을 표시할 요소

  fetch(`/api/boards/comment?cnum=${currentCnum}&reqPage=${reqPage}&reqCnt=${reqCnt}`)
    .then(response => response.json())
    .then(data => {
      if (data.header.rtcd === '00') {
        const comments = data.body;
        $commentList.innerHTML = ''; // 댓글 목록을 초기화

        // 각 댓글을 HTML에 추가
        comments.forEach(comment => {
          const li = document.createElement('li');
          li.classList.add('item', 'read');
          li.dataset.replyNumber = comment.ccommentId; // 댓글 번호를 dataset에 저장

          const span = document.createElement('span');
          span.textContent = comment.contents;
          li.appendChild(span);

          const modifyBtn = document.createElement('button');
          modifyBtn.classList.add('cmodifyBtn');
          modifyBtn.textContent = '수정';
          li.appendChild(modifyBtn);

          const deleteBtn = document.createElement('button');
          deleteBtn.classList.add('cdelBtn');
          deleteBtn.textContent = '삭제';
          li.appendChild(deleteBtn);

          $commentList.appendChild(li);
        });
      } else {
        // 오류 발생 시 처리 로직
        console.log(data);
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

//등록
document.getElementById('addBtn').addEventListener('click',evt=>{
    //1)유효성체크
    if(comment.value.trim().length < 3) {
      //alert('3글자 이상 입력바랍니다.');
     commentErrMsg.textContent = '3글자 이상 입력바랍니다.';
      return;
    }else{
      commentErrMsg.textContent = '';
    }

    addComment();
    comment.value = '';
    comment.focus();

});

// 수정 버튼 클릭 시
document.querySelector('.list').addEventListener('click', event => {
  if (event.target.classList.contains('cmodifyBtn')) {
    const li = event.target.closest('li');
    const ccommentId = li.dataset.replyNumber;

    // 수정된 댓글 내용 가져오기
    const modifiedComment = li.querySelector('span').textContent;

    // 댓글 번호가 undefined이거나 빈 문자열인 경우 처리
    if (!ccommentId || ccommentId.trim() === '') {
      console.error('댓글 번호가 유효하지 않습니다.');
      return;
    }

    // 수정된 댓글 내용이 있는지 확인하고 수정 모드로 변경
    if (modifiedComment.trim() !== '') {
      // 수정 모드로 변경
      li.innerHTML = '';
      const template = document.getElementById('modifyMode').content.cloneNode(true);
      const input = template.querySelector('input');
      input.value = modifiedComment;
      li.appendChild(template);
      // 수정 버튼 클릭 시 저장 또는 취소 동작 구현
      const saveBtn = li.querySelector('.csaveBtn');
      const cancelBtn = li.querySelector('.ccancelBtn');
      saveBtn.addEventListener('click', () => {
        const updatedComment = input.value;
        updateComment(ccommentId, updatedComment)
          .then(() => {
            // 수정이 성공한 경우에만 댓글 목록을 다시 불러옴
            list();
          })
          .catch(error => {
            console.error('댓글 수정 실패:', error);
          });
      });
      cancelBtn.addEventListener('click', () => {
        list(); // 수정 취소 시 목록 다시 불러오기
      });
    } else {
      console.error('수정된 댓글 내용이 비어 있습니다.');
    }
  }

  // 삭제 버튼 클릭 시
  if (event.target.classList.contains('cdelBtn')) {
    const li = event.target.closest('li');
    const ccommentId = li.dataset.replyNumber;

    // 댓글 번호가 undefined이거나 빈 문자열인 경우 처리
    if (!ccommentId || ccommentId.trim() === '') {
      console.error('댓글 번호가 유효하지 않습니다.');
      return;
    }

    deleteComment(ccommentId)
      .then(() => {
        // 삭제가 성공한 경우에만 댓글 목록을 다시 불러옴
        list();
      })
      .catch(error => {
        console.error('댓글 삭제 실패:', error);
      });
  }
});


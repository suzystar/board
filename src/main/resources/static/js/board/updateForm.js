

  const $listBtn = document.getElementById('listBtn');      //목록버튼
  const $updateBtn = document.getElementById('updateBtn');  //저장버튼

  //목록
  $listBtn.addEventListener('click',evt=>{
    location.href = '/boards';      // GET http://서버주소 or 서버도메인/boards
  }, false);

  //저장버튼 , 수정
  $updateBtn.addEventListener('click',evt=>{
    $editModal.showModal();
  });



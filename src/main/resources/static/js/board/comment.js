/////*--댓글부분--*/
//    const commentWrite = () => {
//            const writer = document.getElementById("commentWriter").value;
//            const contents = document.getElementById("commentContents").value;
//            console.log("작성자: ", writer);
//            console.log("내용: ", contents);
//            const id = [[${board.id}]];
//            $.ajax({
//               // 요청방식: post, 요청주소: /comment/save, 요청데이터: 작성자, 작성내용, 게시글번호
//               type: "post",
//               url: "/comment/save",
//               data: {
//                   "commentWriter": writer,
//                   "commentContents": contents,
//                   "boardId": id
//               },
//               success: function (res) {
//                   console.log("요청성공", res);
//                   let output = "<table>";
//                   output += "<tr><th>댓글번호</th>";
//                   output += "<th>작성자</th>";
//                   output += "<th>내용</th>";
//                   output += "<th>작성시간</th></tr>";
//                   for (let i in res) {
//                       output += "<tr>";
//                       output += "<td>" + res[i].id + "</td>";
//                       output += "<td>" + res[i].commentWriter + "</td>";
//                       output += "<td>" + res[i].commentContents + "</td>";
//                       output += "<td>" + res[i].commentCreatedTime + "</td>";
//                       output += "</tr>";
//                   }
//                   output += "</table>";
//                   document.getElementById('comment-list').innerHTML = output;
//                   document.getElementById('commentWriter').value = '';
//                   document.getElementById('commentContents').value = '';
//               },
//               error: function (err) {
//                   console.log("요청실패", err);
//               }
//            });

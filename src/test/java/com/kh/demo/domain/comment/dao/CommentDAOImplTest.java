package com.kh.demo.domain.comment.dao;

import com.kh.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CommentDAOImplTest {

  @Autowired
  CommentDAO commentDAO;

  @Test
  @DisplayName("댓글작성테스트")
  void save() {
    Comment comment = new Comment();
    comment.setCnum(1L);
    comment.setContents("댓글");
    comment.setPname("유저1");

    Long userId = commentDAO.save(comment);
  }


//  @Test
//  void findById() {
//    Long ccommentId = 3L;
//    Optional<Comment> findedComment = commentDAO.findById(ccommentId);
//    Comment comment = findedComment.orElse(null);
//    log.info("comment={}", comment);
//  }

  @Test
  void deleteById() {
    Long ccommentId = 4L;
    int deletedRowCnt = commentDAO.deleteById(ccommentId);
  }

  @Test
  @DisplayName("댓글수정테스트")
  void updateById() {
    Long ccommentId = 3L;
    Comment comment = new Comment();
    comment.setPname("2L");
    comment.setContents("댓글수정테스트");
    comment.setPname("홍길남3");
    int updatedRowCnt = commentDAO.updateById(ccommentId, comment);
    log.info("updatedRowCnt={}", updatedRowCnt);
  }



  @Test
  @DisplayName("댓글여러건등록테스트")
  void testSave() {
    long start = 1;
    long end = 120;
    for (long i = start; i <= end; i++) {
      Comment comment = new Comment();
      comment.setCnum(1L);
      comment.setContents("댓글"+i);
      comment.setPname("작성자"+i);
      log.info("comment={}",comment);
      Long ccommentId = commentDAO.save(comment);
    }
  }

}
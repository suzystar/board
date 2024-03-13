package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BoardDAOImplTest {

  @Autowired
  BoardDAO boardDAO;

  @Test
  @DisplayName("게시판여러건등록")
  void testWrite() {
    long start = 1;
    long end = 120;
    for (long i = start; i <= end; i++) {
      Board board = new Board();
      board.setTitle("제목" + i);
      board.setContents("내용" + i);
      board.setPname("작성자");
      Long boardId = boardDAO.save(board);
    }
  }
}
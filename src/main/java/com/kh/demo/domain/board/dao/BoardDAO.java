package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDAO {
  //등록
  Long save(Board board);
  //조회
  //Optional 객체를 최대 1개를 저장할수 있는 컬렉션
  Optional<Board> findById(Long userId);

  //목록
  List<Board> findAll();
  //목록(페이징)
  List<Board> findAll(Long reqPage, Long recCnt);

  //단건삭제
  int deleteById(Long userId);


  //여러건삭제
  int deleteByIds(List<Long> userIds);

  //수정
 int updateById(Long userId,Board board);

  //총레코드 건수
  int totalCnt();
}

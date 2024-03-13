package com.kh.demo.domain.board.svc;

import com.kh.demo.domain.entity.Board;

import java.util.List;
import java.util.Optional;


  public interface BoardSVC {
    //등록
    Long save(Board board);

    //조회
    //Optional 객체를 최대 1개를 저장할수 있는 컬렉션
    Optional<Board> findById(Long userId);


    //단건삭제
    int deleteById(Long userId);

    //여러건삭제
    int deleteByIds(List<Long> userIds);

    //목록
    List<Board> findAll();
    //목록(페이징)
    List<Board> findAll(Long reqPage, Long reqCnt);

    //수정
    int updateById(Long userId, Board board);
    //총레코드 건수
    int totalCnt();
  }

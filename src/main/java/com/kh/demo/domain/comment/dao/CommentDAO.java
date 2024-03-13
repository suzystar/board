package com.kh.demo.domain.comment.dao;


import com.kh.demo.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
  //등록
  Long save(Comment comment);
  //조회
  //Optional 객체를 최대 1개를 저장할수 있는 컬렉션
  Optional<Comment> findById(Long ccommnentId);

//  //목록
//  List<Comment> findAll();
  //목록(페이징)
  List<Comment> findAll(Long cnum, Long reqPage, Long reqCnt);

  //단건삭제
  int deleteById(Long ccommentId);


  //수정
  int updateById(Long ccommentId,Comment comment);

  //총레코드 건수
  int totalCnt();
}

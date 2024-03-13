package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
 private Long ccommentId;  //number(10),  -- 아이디
 private Long cnum;        //number(10),  -- 원글번호
 private String contents;   // varchar2(50),   -- 내용
 private String pname ;     // varchar2(30),-- 작성자
 private LocalDateTime cdate ;   //   timestamp,   --작성날짜
 private LocalDateTime udate ;     // timestamp    --수정날짜
}

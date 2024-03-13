package com.kh.demo.domain.entity;
// wrapper class
// byte->Byte, short->Short, char->Character, int->Integer, long->Long
// boolean->Boolean, double->Double, float->Float

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
  private Long userId;         //게시글 식별 아이디 user_ID	NUMBER(10,0)
  private String pname;           //사용자 이름 PNAME	VARCHAR2(30 BYTE)
  private String title;           //글 제목 String VARCHAR2(30 BYTE)
  private String contents;        //글내용 contents	 VARCHAR2(50 BYTE)
  private String email;           //이메일
  private LocalDateTime cdate;     //생성일시 cdate CDATE	TIMESTAMP(6)
  private LocalDateTime udate;    //수정일시 UDATE	TIMESTAMP(6)
}



package com.kh.demo.web.req.comment;

import lombok.Data;

@Data
public class ReqWrite {
  private Long cnum;
  private String pname;
  private String contents;
}
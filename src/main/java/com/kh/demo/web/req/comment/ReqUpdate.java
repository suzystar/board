package com.kh.demo.web.req.comment;

import lombok.Data;

@Data
public class ReqUpdate {
  private Long cnum;
  private String pname;
  private String contents;
}
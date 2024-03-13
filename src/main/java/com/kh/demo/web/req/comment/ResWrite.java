package com.kh.demo.web.req.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResWrite {
  private Long ccommentId;
  private String contents;
}
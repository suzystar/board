package com.kh.demo.web.form.board;

import lombok.Data;

@Data
public class UpdateForm {
  private Long userId;
  private String title;
  private String contents;
  private String pname;
  private String email;
}


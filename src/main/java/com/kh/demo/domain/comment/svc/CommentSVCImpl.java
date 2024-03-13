package com.kh.demo.domain.comment.svc;

import com.kh.demo.domain.comment.dao.CommentDAO;
import com.kh.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentSVCImpl implements CommentSVC {
  private CommentDAO commentDAO;

  public CommentSVCImpl(CommentDAO commentDAO){
    this.commentDAO = commentDAO;
  }


  @Override
  public Long save(Comment comment) {
    return commentDAO.save(comment);
  }

  @Override
  public Optional<Comment> findById(Long ccomentId){
    return  commentDAO.findById(ccomentId);
  }


  @Override
  public List<Comment> findAll(Long cnum, Long reqPage, Long recCnt) {
    return commentDAO.findAll(cnum, reqPage, recCnt);
  }

  @Override
  public int deleteById(Long ccomentId) {
    return commentDAO.deleteById(ccomentId);
  }

  @Override
  public int updateById(Long ccomentId, Comment comment) {
    return commentDAO.updateById(ccomentId, comment);
  }

  @Override
  public int totalCnt() {
    return commentDAO.totalCnt();
  }




}

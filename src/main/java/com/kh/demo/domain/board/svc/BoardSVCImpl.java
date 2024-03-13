package com.kh.demo.domain.board.svc;

import com.kh.demo.domain.entity.Board;
import com.kh.demo.domain.board.dao.BoardDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class BoardSVCImpl implements BoardSVC {

  private BoardDAO boardDAO;

  BoardSVCImpl(BoardDAO boardDAO) {
    this.boardDAO = boardDAO;
  }

  @Override
  public Long save(Board board) {
    return boardDAO.save(board);
  }

  @Override
  public Optional<Board> findById(Long userId) {
    return boardDAO.findById(userId);
  }

  @Override
  public int deleteById(Long userId) {
    return boardDAO.deleteById(userId);
  }

  @Override
  public int deleteByIds(List<Long> userIds) {
    return boardDAO.deleteByIds(userIds);
  }

  @Override
  public int updateById(Long userId, Board board) {
    return boardDAO.updateById(userId, board);
  }

  @Override
  public List<Board> findAll() {
    return boardDAO.findAll();
  }
  @Override
  public List<Board> findAll(Long reqPage, Long recordCnt) {
    return boardDAO.findAll(reqPage,recordCnt);
  }

  @Override
  public int totalCnt() {
    return boardDAO.totalCnt();
  }
}
package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository //dao역할을 하는 클래스
public class BoardDAOImpl implements BoardDAO {

  private final NamedParameterJdbcTemplate template;

  BoardDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  //등록
  @Override
  public Long save(Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into Board(user_id,pname,title,contents) ");
    sql.append("values(user_user_id_seq.nextval, :pname, :title, :contents) ");

    // SQL파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(board);
    KeyHolder keyHolder = new GeneratedKeyHolder();
//    template.update(sql.toString(),param,keyHolder,new String[]{"user_id"});
    template.update(sql.toString(),param,keyHolder,new String[]{"user_id","pname","title","contents"});
//    long userId = keyHolder.getKey().longValue(); //상품아이디
//    log.info("keyHolder={}", keyHolder.getKeys());
    Long user_id = ((BigDecimal)keyHolder.getKeys().get("user_id")).longValue(); //아이디
    return user_id;
  }

//조회
  @Override
  public Optional<Board> findById(Long userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select user_id,pname,title,contents,cdate,udate ");
    sql.append("  from board ");
    sql.append(" where user_id = :userId ");

    try {
      Map<String,Long> map = Map.of("userId",userId);
      Board board = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Board.class));
      return Optional.of(board);

    }catch (EmptyResultDataAccessException e){
      //조회결과가 없는경우
      return Optional.empty();
    }
  }

  //단건삭제
  @Override
  public int deleteById(Long userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from Board ");
    sql.append(" where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("userId",userId);

    int deletedRowCnt = template.update(sql.toString(), param);

    return deletedRowCnt;
  }

  //여러건 삭제
  @Override
  public int deleteByIds(List<Long> userIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from Board ");
    sql.append(" where user_id in (:userIds) ");

    Map<String,List<Long>> map = Map.of("userIds",userIds);
    int deletedRowCnt = template.update(sql.toString(), map);
    return deletedRowCnt;
  }

  //수정
  @Override
  public int updateById(Long userId, Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("update Board ");
    sql.append("   set pname = :pname, ");
    sql.append("       title = :title, ");
    sql.append("       contents = :contents, ");
    sql.append("       udate = default ");
    sql.append(" where user_id = :userId ");

    //sql 파라미터 변수에 값 매핑
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("pname", board.getPname())
        .addValue("title", board.getTitle())
        .addValue("contents", board.getContents())
        .addValue("userId", userId);

    //update수행 후 변경된 행수 반환
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }

  //목록
  @Override
  public List<Board> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("  select user_id,pname,title,contents,cdate,udate ");
    sql.append("    from board ");
    sql.append("order by user_id desc ");

    List<Board> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Board.class));
    return list;
  }
  @Override
  public List<Board> findAll(Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append("  select user_id,pname,title,contents,cdate,udate ");
    sql.append("    from board ");
    sql.append("order by user_id asc ");
    sql.append("offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");

    Map<String,Long> param = Map.of("reqPage",reqPage,"recCnt",recCnt);
    List<Board> list = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Board.class));

    return list;
  }

  //총레코드 건수
  @Override
  public int totalCnt() {
    String sql = "SELECT COUNT(user_id) FROM board ";

    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt;
  }

}
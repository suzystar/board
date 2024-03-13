package com.kh.demo.domain.comment.dao;

import com.kh.demo.domain.entity.Comment;
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
@Repository
public class CommentDAOImpl implements CommentDAO{

  private final NamedParameterJdbcTemplate template;

  CommentDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  //댓글 작성
  @Override
  public Long save(Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into ccomment(ccomment_id, cnum, contents, pname) ");
    sql.append("values(Ccomment_ccomment_id_seq.nextval, :cnum, :contents, :pname) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"ccomment_id", "cnum", "contents", "pname"});
    Long ccomment_id = ((BigDecimal)keyHolder.getKeys().get("ccomment_id")).longValue();
    return ccomment_id;
  }

  //조회
  @Override
  public Optional<Comment> findById(Long ccommentId) {

    StringBuffer sql = new StringBuffer();
    sql.append("select ccommentId,cnum,contents,pname,cdate,udate ");
    sql.append("  from ccomment ");
    sql.append(" where ccommentId = :ccommentId ");

    try{
      Map<String, Long> map = Map.of("ccommentId",ccommentId);
      Comment comment = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Comment.class));
      return Optional.of(comment);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  //삭제
  @Override
  public int deleteById(Long ccommentId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from ccomment ");
    sql.append("where ccomment_id = :ccommentId"); // 수정된 부분
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("ccommentId", ccommentId);
    int deletedRowCnt = template.update(sql.toString(), param);
    return deletedRowCnt;
  }

  //수정
  @Override
  public int updateById(Long ccommentId, Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("update ccomment ");
    sql.append("set contents = :contents "); // 수정된 부분, 불필요한 콤마 제거
    sql.append("where ccomment_id = :ccommentId"); // 수정된 부분
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("contents", comment.getContents())
        .addValue("ccommentId", ccommentId); // 추가된 부분
    int updateRowCnt = template.update(sql.toString(), param);
    return updateRowCnt;
  }
  //목록
  @Override
  public List<Comment> findAll(Long cnum, Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append("  select ccomment_id,cnum,contents,pname,cdate,udate ");
    sql.append("    from ccomment ");
    sql.append("where cnum = :cnum ");
    sql.append("offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");

    Map<String,Long> param = Map.of("cnum", cnum, "reqPage",reqPage,"recCnt",recCnt);
    List<Comment> list = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Comment.class));

    return list;
  }
  @Override
  public int totalCnt() {
    String sql = "select count(ccomment_id) from ccomment ";

    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt;
  }

 }



package com.kh.demo.web;

import com.kh.demo.domain.board.svc.BoardSVC;
import com.kh.demo.domain.entity.Board;
import com.kh.demo.web.form.board.AddForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Slf4j
@Controller // Controller 역할을 하는 클래스
@RequestMapping("/boards")    // http://localhost:9080/boards
public class BoardController {

  private BoardSVC boardSVC;

  BoardController(BoardSVC boardSVC){this.boardSVC = boardSVC;}

  //게시글등록양식
  @GetMapping("/add")         // Get, http://localhost:9080/boards/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "board/add";     // view이름  게시글등록화면
  }

  //게시글등록처리
  @PostMapping("/add")        // Post, http://localhost:9080/boards/add
  public String add(
      AddForm addForm,
      Model model,
      RedirectAttributes redirectAttributes
  ){

    //게시글등록
    Board board = new Board();
    board.setTitle(addForm.getTitle());
    board.setContents(addForm.getContents());
    board.setPname(addForm.getPname());

    Long userId = boardSVC.save(board);
    log.info("게시글번호={}", userId);

    redirectAttributes.addAttribute("pid",userId);
    return "redirect:/boards/{pid}/detail"; // 게시글조회화면  302 GET http://서버:9080/boards/게시글번호/detail
  }

  //조회
  @GetMapping("/{pid}/detail")       //GET http://localhost:9080/boards/게시글번호/detail
  public String findById(@PathVariable("pid") Long userId, Model model){

    Optional<Board> findedBoard = boardSVC.findById(userId);
    Board board = findedBoard.orElseThrow();
    model.addAttribute("board", board);

    return "board/detailForm";
  }


  //단건삭제
  @GetMapping("/{pid}/del")
  public String deleteById(@PathVariable("pid") Long userId){
    log.info("deleteById={}",userId);

    //1)게시글 삭제 -> 게시글
    // 테이블에서 삭제
    int deletedRowCnt = boardSVC.deleteById(userId);

    return "redirect:/boards";     // GET http://localhost:9080/boards/
  }

  //여러건삭제
  @PostMapping("/del")          // POST http://localhost:9080/boards/del
  public String deleteByIds(@RequestParam("pids") List<Long> pids) {

    log.info("deleteByIds={}",pids);
    int deletedRowCnt = boardSVC.deleteByIds(pids);

    return "redirect:/boards";    // GET http://localhost:9080/boards/
  }

  //수정양식
  @GetMapping("/{pid}/edit")      // GET http://locahost:9080/boards/상품번호/edit
  public String updateForm(
      @PathVariable("pid") Long userId,
      Model model){

    Optional<Board> optionalboard = boardSVC.findById(userId);
    Board findedboard = optionalboard.orElseThrow();

    model.addAttribute("board",findedboard);
    return "board/updateForm";
  }
  //수정 처리
  @PostMapping("/{pid}/edit")
  public String update(
      //경로변수 pid로부터 상품번호을 읽어온다
      @PathVariable("pid") Long userId,
      //요청메세지 바디로부터 대응되는 상품정보를 읽어온다.
      @RequestParam("pname") String pname,
      @RequestParam("title") String title,
      @RequestParam("contents") String contents,
      //리다이렉트시 경로변수에 값을 설정하기위해 사용
      RedirectAttributes redirectAttributes){

    Board board = new Board();
    board.setPname(pname);
    board.setTitle(title);
    board.setContents(contents);
    int updateRowCnt = boardSVC.updateById(userId, board);

    redirectAttributes.addAttribute("pid",userId);
    return "redirect:/boards/{pid}/detail";
  }

//  //목록
//  @GetMapping   // GET http://localhost:9080/boards
//  public String findAll(Model model) {
//
//    List<Board> list = boardSVC.findAll();
//    model.addAttribute("list", list);
//
//    return "board/all";
//}
    //목록(페이징)
    @GetMapping   // GET http://localhost:9080/boards?reqPage=2&reqCnt=10
    public String findAllByPaging(
            Model model,
            @RequestParam(value = "reqPage", defaultValue = "1") Long reqPage, // 요청 페이지
            @RequestParam(value = "reqCnt", defaultValue = "10") Long reqCnt,   // 레코드 수
            @RequestParam(value = "cpgs", defaultValue = "1") Long cpgs,   //페이지 그룹 시작번호
            @RequestParam(value = "cp", defaultValue = "1") Long cp   // 현재 페이지
    ){

      List<Board> list = boardSVC.findAll(reqPage, reqCnt);
      int totalCnt = boardSVC.totalCnt();

      model.addAttribute("list", list);
      model.addAttribute("totalCnt", totalCnt);
      model.addAttribute("cpgs", cpgs);
      model.addAttribute("cp", cp);

      return "board/all";
    }

}
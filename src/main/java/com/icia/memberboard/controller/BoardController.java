package com.icia.memberboard.controller;

import com.icia.memberboard.common.PagingConst;
import com.icia.memberboard.dto.*;
import com.icia.memberboard.service.BoardService;
import jdk.jfr.MemoryAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService bs;


    // 게시판 글쓰기
    @GetMapping("/save")
    public String saveForm(){
        return "board/save";
    }

    // 게시판 글쓰기 처리
    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
    // 타임리프를 사용안하면 ModelAttribute 제외 가능
        Long boardId = bs.save(boardSaveDTO);
        return "redirect:/board/";
    }

    // 글목록
    // 주소요청 /board/
//    @GetMapping("/")
//    public String findAll(Model model) {
//        List<BoardDetailDTO> boardList = bs.findAll();
//        model.addAttribute("boardList", boardList);
//        return "board/findAll";
//    }

    // 페이징 처리 (/board?page=5)
    // 5번글(/board/5) //조회할때 5번이라는것은 고유번호이다. 페이지는 계속변하기떄문에 고유번호 ㄴㄴ
    // PageableDefault(page=1) : page 라는 파라미터를 가져옴.(페이지요청값이 없을경우 1페이지를 띄우겠다) 한마디로 페이지 기본값
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        // Page 객체 // 서비스로 넘기는건 pageable 객체를 넘긴다
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);
        // 삼항연산자로 바꿈
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/paging";
    }

    // 상세조회
    // (/board/5)
    // @PathVariable : 경로상에 있는 변수를 가져올때 사용
    @GetMapping("/{boardId}")
    public String findById(@PathVariable Long boardId, Model model) {
        log.info("글보기 메서드 호출. 요청글번호: {}", boardId);
        //리턴 받아오기
        BoardDetailDTO board = bs.findById(boardId);
        // 모델에 담아서 넘기기
        model.addAttribute("board", board);
        model.addAttribute("comment", board.getCommentList());
        return "board/findById";
    }

    // 회원삭제  (/member/5)
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteById (@PathVariable Long boardId) {
        bs.deleteById(boardId);
        /*
            단순 화면 출력이 아닌 데이터를 리턴하고자 할 때 사용하는 리턴 방식
            ResponseEntity: 데이터 & http 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
            @ReponseBody: 데이터를 리턴할 수 있음.
         */
        // 200 코드를 리턴
        return new ResponseEntity(HttpStatus.OK);
    }

    // 업데이트 페이지 출력
    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model){
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        return "/board/update";

    }

    // 업데이트 Put
    @PutMapping("/{boardId}")
    public ResponseEntity update(@RequestBody BoardUpdateDTO boardUpdateDTO) {
        System.out.println("boardUpdateDTO = " + boardUpdateDTO);
        bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 서치
    @GetMapping("/search")
    public String search(@ModelAttribute BoardSearchDTO boardSearchDTO, Model model) {
        // 검색결과를 가지고 가야하니까 Model
        List<BoardDetailDTO> boardDetailDTOList = bs.search(boardSearchDTO);
        model.addAttribute("boardList", boardDetailDTOList);
        return "/board/search";

    }


}

package com.icia.memberboard.controller;

import com.icia.memberboard.dto.CommentDetailDTO;
import com.icia.memberboard.dto.CommentSaveDTO;
import com.icia.memberboard.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService cs;

    @PostMapping("/save")
    public @ResponseBody List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO) {
        System.out.println("commentSaveDTO = " + commentSaveDTO);
        Long commentId = cs.save(commentSaveDTO);
        // 댓글 등록후 리스트뽑기 boardId를 넘김
        List<CommentDetailDTO> commentList = cs.findAll(commentSaveDTO.getBoardId());

        return commentList;
    }
}

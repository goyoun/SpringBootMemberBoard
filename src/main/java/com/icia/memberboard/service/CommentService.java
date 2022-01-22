package com.icia.memberboard.service;

import com.icia.memberboard.dto.CommentDetailDTO;
import com.icia.memberboard.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {

    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);
}

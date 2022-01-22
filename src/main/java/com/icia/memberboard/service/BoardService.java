package com.icia.memberboard.service;

import com.icia.memberboard.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO) throws IOException;

    List<BoardDetailDTO> findAll();

    Page<BoardPagingDTO> paging(Pageable pageable);

    BoardDetailDTO findById(Long boardId);

    void deleteById(Long boardId);

    void update(BoardUpdateDTO boardUpdateDTO);

    List<BoardDetailDTO> search(BoardSearchDTO boardSearchDTO);
}

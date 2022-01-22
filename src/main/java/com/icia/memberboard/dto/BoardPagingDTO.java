package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardPagingDTO {
    private Long boardId;
    private String boardWriter;
    private String boardTitle;
    private int boardHits;
}

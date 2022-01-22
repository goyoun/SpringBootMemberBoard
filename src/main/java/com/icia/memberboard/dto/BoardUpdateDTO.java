package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDTO {
    private Long boardId;
    private Long memberId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String boardFilename;
}

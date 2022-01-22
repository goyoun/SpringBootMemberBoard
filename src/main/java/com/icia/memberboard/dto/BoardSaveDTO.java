package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {
    private String memberEmail;
    private String boardTitle;
    private String boardContents;
    private MultipartFile bFile;
    private String bFilename;
}

package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private MultipartFile mFile;
    private String mFilename;

}

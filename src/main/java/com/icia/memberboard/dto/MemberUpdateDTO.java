package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberUpdateDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private String memberFilename;
    
}

package com.icia.memberboard.dto;

import com.icia.memberboard.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO {

    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberPassword;
    private String memberFilename;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity memberEntity) {
        // 객체 생성
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        // 옮겨닮기
        memberDetailDTO.setMemberId(memberEntity.getId());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        memberDetailDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDetailDTO.setMemberFilename(memberEntity.getMFilename());
        // 리턴
        return memberDetailDTO;
    }

}

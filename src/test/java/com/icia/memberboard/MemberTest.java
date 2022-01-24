package com.icia.memberboard;

import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.repository.MemberRepository;
import com.icia.memberboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;
    @Autowired
    private MemberRepository mr;

    @Test
    @DisplayName("회원가입 테스트")
    public void memberSaveTest() throws IOException {
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("회원가입테스트 이메일1");
        memberSaveDTO.setMemberPassword("회원가입테스트 비밀번호1");
        memberSaveDTO.setMemberName("회원가입테스트 이름1");
        memberSaveDTO.setMemberPhone("회원가입테스트 폰번호1");
        memberSaveDTO.setMFilename("1111-111");
        ms.save(memberSaveDTO);
    }

    
}

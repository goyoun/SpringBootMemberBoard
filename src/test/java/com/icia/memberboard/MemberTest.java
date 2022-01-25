package com.icia.memberboard;

import com.icia.memberboard.dto.BoardSaveDTO;
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
    


}

package com.icia.memberboard.service;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO) throws IOException;

//    boolean login(MemberLoginDTO memberLoginDTO);

    MemberDetailDTO findById(Long memberId);

    String emailCheck(String memberEmail);

    List<MemberDetailDTO> findAll();

    void deleteById(Long memberId);

    Long update(MemberDetailDTO memberDetailDTO);

    boolean login(MemberLoginDTO memberLoginDTO);
}

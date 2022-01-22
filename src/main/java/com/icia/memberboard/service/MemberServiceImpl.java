package com.icia.memberboard.service;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    // 회원가입
    @Override
    public Long save(MemberSaveDTO memberSaveDTO) throws IOException, IllegalStateException{
        MultipartFile mFile = memberSaveDTO.getMFile();
        System.out.println(mFile);
        String mFilename = mFile.getOriginalFilename();
        mFilename = System.currentTimeMillis() + "-" + mFilename;
        String savePath = "C:\\Development\\source\\springboot\\MemberBoard\\src\\main\\resources\\static\\image\\" + mFilename;
        if (!mFile.isEmpty()) {
            mFile.transferTo(new File(savePath));
        }
        memberSaveDTO.setMFilename(mFilename);
        System.out.println("memberSaveDTO.getMemberFilename() = " + memberSaveDTO.getMFilename());

        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberSaveDTO);
        return mr.save(memberEntity).getId();
    }


    // 로그인처리
    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
// 1. 사용자가 입력한 이메일을 조건으로 DB에서 조회 (select * from member_table where member_email=?)
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        // 2. 비밀번호 일치여부 확인
        if (memberEntity != null) {
            if (memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //회원 상세조회
    @Override
    public MemberDetailDTO findById(Long memberId) {
//            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴.
//            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴.

        // 1.
        // Optional = null 방지
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        //.get() 은 옵셔널 안에 Entity를 꺼낸다
        MemberEntity memberEntity = memberEntityOptional.get();

        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
        // 위 네줄을 한줄로 표현하면
        //return MemberDetailDTO.toMemberDetailDTO(mr.findById(memberId).get());
    }

    // 이메일 충복 체크
    @Override
    public String emailCheck (String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        if (memberEntity == null) {
            return "ok";
        } else {
            return "no";
        }
    }

    // 회원 전체 조회
    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for (MemberEntity m: memberEntityList) {
            memberList.add(MemberDetailDTO.toMemberDetailDTO(m));
        }
        return memberList;
    }

    // 겟매핑 회원삭제
    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }


    // 업데이트 put
    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        // JPA는 update 처리시 save 메서드 호출
        // MemberDetailDTO -> MemberEntity 로 변경
        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberDetailDTO);
        Long memberId = mr.save(memberEntity).getId();
        return memberId;
    }


}

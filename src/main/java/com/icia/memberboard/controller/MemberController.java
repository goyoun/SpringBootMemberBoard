package com.icia.memberboard.controller;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberLoginDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.MemberRepository;
import com.icia.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.List;

import static com.icia.memberboard.common.SessionConst.LOGIN_EMAIL;
import static com.icia.memberboard.common.SessionConst.MEMBER_ID;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;
    private final MemberRepository mr;

    // 회원가입페이지
    @GetMapping("save")
    public String saveForm(){
        return "member/save";
    }

    // 회원가입처리
    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) throws IOException, IllegalStateException{
        Long memberId = ms.save(memberSaveDTO);
        return "member/login";
    }

    // 로그인페이지
    @GetMapping("login")
    public String loginForm() {
        return "member/login";
    }

    // 로그인처리
    @PostMapping("login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session) {
        boolean loginResult = ms.login(memberLoginDTO);
        if (loginResult) {
            MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
//            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
            session.setAttribute(MEMBER_ID, memberDetailDTO.getMemberId());

            return "redirect:/board/";
        } else {
            return "member/login";
        }
    }
    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    // 이메일 중복체크
    @PostMapping("emailCheck") //에이잭스 ResponseBody 필수 (String 타입의 문자를 그대로 보내주는 역할)
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String result = ms.emailCheck(memberEmail);
        System.out.println("이메일 중복 체크");
        return result;
    }

//    }

    // 회원조회
    // (/member/5)
    // @PathVariable: 경로상에 있는 변수를 가져올 때 사용
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member",member);
        return"member/findById";
    }

    // 회원조회 ajax
    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable("memberId") Long memberId) {
        MemberDetailDTO member = ms.findById(memberId);
        return member;
    }

    // 회원목록
    @GetMapping
    public String FindAll(Model model) {
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        return "member/findAll";
    }

    // 회원삭제 (/member/delete/1)
    @GetMapping("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);
        return "redirect:/member/";
    }

    // 회원삭제 DeleteMapping
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById2(@PathVariable Long memberId) {
        ms.deleteById(memberId);
    return new ResponseEntity(HttpStatus.OK);

    }

    // 마이페이지
    @GetMapping("mypage/{memberId}")
    public String mypageForm(@PathVariable Long memberId, Model model) {
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);
        model.addAttribute("member",memberDetailDTO);
        return "member/mypage";
    }

    // 업데이트 화면요청
    @GetMapping("update")
    public String updateForm(Model model, HttpSession session) {
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        model.addAttribute("member", memberDetailDTO);
        return "member/update";
    }

    // 업데이트 put방식
    @PutMapping("{memberId}")
    public ResponseEntity update(@RequestBody MemberDetailDTO memberDetailDTO) {
        Long memberId = ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 관리자페이지
    @GetMapping("adminPage")
    public String adminPage() {
        return "member/adminPage";
    }

}

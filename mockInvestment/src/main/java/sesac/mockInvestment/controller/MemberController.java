package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sesac.mockInvestment.argumentresolver.Login;
import sesac.mockInvestment.domain.*;
import sesac.mockInvestment.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @ModelAttribute("gender")
    public GenderType[] gender() {
        System.out.println("GenderType"+Arrays.toString(GenderType.values()));
        return GenderType.values();
    }

    @GetMapping("/member")
    public String signupForm(@ModelAttribute RegisterMemberFormDto registerMemberFormDto) {
        return "member/registerMember";
    }

    @PostMapping("/member")
    public String signup(@Validated  @ModelAttribute RegisterMemberFormDto registerMemberFormDto,
                         BindingResult bindingResult) throws SQLException {


        log.info("DTO111 {}", registerMemberFormDto.toString());
        if (!registerMemberFormDto.getPassword1().equals(registerMemberFormDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            return "member/registerMember";
        }
        if(bindingResult.hasErrors()){
            log.info("errors111={}", bindingResult);

            return "member/registerMember";
        }

        // 아이디 중복 체크를 수행
        if (memberService.isIdDuplicated(registerMemberFormDto.getId())) {
            bindingResult.rejectValue("id", "duplicateId", "중복된 아이디입니다.");
            return "member/registerMember";
        }

        log.info("save 실행하기 전");
        memberService.save(registerMemberFormDto);
        System.out.println("직원등록 11");



        return "redirect:/login";
    }

    @GetMapping("/mypage")
    public String mypage(@Login LoginMemberFormDto LoginMemberFormDto, Model model) {

        return "member/mypage";
    }

    @GetMapping("/editMember")
    public String editMember(@Login LoginMemberFormDto LoginMemberFormDto, Model model, @SessionAttribute("loginMember") MemberDto loginMember) {

        System.out.println(loginMember+"loginMember DTO체크");
        // loginMember 안에 있는 memberDTO를 Model에 추가
        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }

        return "member/editMember";
    }


    @PostMapping("/editMember")
    public String editMemberPost(@ModelAttribute EditMemberFormDto memberDto, @ModelAttribute GenderType genderType, BindingResult bindingResult, @SessionAttribute("loginMember") MemberDto loginMember, Model model) {

        System.out.println(memberDto.toString());
        System.out.println(loginMember.toString());

        model.addAttribute("loginMember", loginMember);

        log.info("업데이트 dto확인 = {}",memberDto);
        if (!memberDto.getPassword1().equals(memberDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            return "member/editMember";
        }
        if(bindingResult.hasErrors()){
            log.info("errors2222={}", bindingResult);

            return "member/editMember";
        }



        log.info("update 비밀번호 검증 실행하기 전");
        if(memberService.login(memberDto.getId(), memberDto.getOriPassword()) != null){
            log.info("update 실행하기 전");
            memberService.update(memberDto);
            System.out.println("직원정보 수정 11");
        } else{
            System.out.println("직원정보 수정 실패");
        }

        return "member/editMember";
    }

    @PostMapping("/deleteMember")
    public String deleteMemberPost(@ModelAttribute DeleteMemberFormDto deleteMemberFormDto,BindingResult bindingResult, @SessionAttribute("loginMember") MemberDto loginMember, Model model, HttpServletRequest request) {

        deleteMemberFormDto.setId(loginMember.getId());

        System.out.println("deleteMemberFormDto"+memberService.login(deleteMemberFormDto.getId(), deleteMemberFormDto.getDeletePassword()));

        if(bindingResult.hasErrors()){
            log.info("errors2222={}", bindingResult);

            return "member/editMember";
        }

        if(memberService.login(deleteMemberFormDto.getId(), deleteMemberFormDto.getDeletePassword()) != null){
            log.info("delete 실행하기 전");
            memberService.delete(deleteMemberFormDto);
            System.out.println("직원정보 삭제11 11");
            // 세션 삭제
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }


        return "index";
    }
}

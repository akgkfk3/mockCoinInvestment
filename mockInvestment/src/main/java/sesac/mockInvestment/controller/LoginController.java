package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.repository.MemberDao;
import sesac.mockInvestment.service.MemberService;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final MemberDao memberDao;

    @GetMapping("/login")
    public String loginForm() {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginMemberFormDto loginMemberFormDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("error login={}",bindingResult);
            return "/login";
        }


        try{
//            if(memberService.selectById(loginMemberFormDto).equals(LoginMemberFormDto.getId())){
//
//                System.out.println("로그인 가능111");
//            };

            // 아이디로 회원 조회
//            MemberDto member = memberService.selectById(loginMemberFormDto.getId());
            memberDao.findById(loginMemberFormDto.getId());
            System.out.println(memberDao.findById(loginMemberFormDto.getId())+"memberDao111");
//            System.out.println("selectALL 확인");
//            memberDao.selectAll();


//            if (member != null && member.getPassword().equals(loginMemberFormDto.getPassword())) {
//                // 로그인 성공
//                session.setAttribute("loggedInUser", member);
//                return "redirect:/"; // 로그인 후 리다이렉트할 경로
//            } else {
//                // 로그인 실패
//                bindingResult.rejectValue("id", "loginFailed", "아이디 또는 비밀번호가 올바르지 않습니다.");
//                return "/login"; // 로그인 폼 템플릿 이름
//            }
        }catch (SQLException e){
            e.getStackTrace();
            return "/login";
        }

        System.out.println("로그인 성공");

        return "redirect:/";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "/login/forgotPassword";
    }


}

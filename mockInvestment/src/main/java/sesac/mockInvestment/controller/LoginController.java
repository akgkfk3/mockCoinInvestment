package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sesac.mockInvestment.SessionConst;
import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.repository.MemberDao;
import sesac.mockInvestment.service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final MemberDao memberDao;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginMemberFormDto loginMemberFormDto) {
        return "/login/loginForm";
    }


@PostMapping("/login")
public String login(@Validated @ModelAttribute LoginMemberFormDto loginMemberFormDto, BindingResult bindingResult,
//                      @RequestParam(defaultValue = "/") String redirectURL,
                      HttpServletRequest request, Model model) {
    if (bindingResult.hasErrors()) {
        return "login/loginForm";
    }

    MemberDto loginMember = null;
    try {
        loginMember = memberService.login(loginMemberFormDto.getId(), loginMemberFormDto.getPassword());
        if(loginMember == null){
            return "login/loginForm";
        }
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        System.out.println("userName찾기"+loginMember.getName());
        session.setAttribute("loggedInUserName", loginMember.getName());
        return "index";

    } catch (SQLException e) {
        e.getStackTrace();
        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        return "login/loginForm";
    }

}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "index";
    }


    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "login/forgotPassword";
    }

}

package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.service.MemberService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, BindingResult bindingResult) {

//        memberService.select(memberDto);
        System.out.println("로그인 성공");

        return "redirect:/";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "/login/forgotPassword";
    }


}

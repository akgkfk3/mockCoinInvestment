package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sesac.mockInvestment.domain.RegisterMemberFormDto;
import sesac.mockInvestment.service.MemberService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;



    @GetMapping("/member")
    public String signupForm(@ModelAttribute RegisterMemberFormDto registerMemberFormDto) {
        return "/member/registerMember";
    }

    @PostMapping("/member")
    public String signup(@Validated  @ModelAttribute RegisterMemberFormDto registerMemberFormDto,
                         BindingResult bindingResult) {


        log.info("DTO111 {}", registerMemberFormDto.toString());
        if (!registerMemberFormDto.getPassword1().equals(registerMemberFormDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            return "/member/registerMember";
        }
        if(bindingResult.hasErrors()){
            log.info("errors111={}", bindingResult);

//            bindingResult.rejectValue();

            log.info("error {}", bindingResult.getFieldError("id"));
            log.info("error {}", bindingResult.getFieldError("id"));
            log.info("error {}", bindingResult.getFieldError("id"));
            log.info("error {}", bindingResult.getFieldError("id"));
            log.info("error {}", bindingResult.getFieldError("id"));
            log.info("error {}", bindingResult.getFieldError("id"));

            return "/member/registerMember";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//            Date a = Date.valueOf(simpleDateFormat.format(registerMemberFormDto.getBirthDate()));
//            registerMemberFormDto.setBirthDate(a);
            log.info("save 실행하기 전");
            memberService.save(registerMemberFormDto);
            System.out.println("직원등록 11");
        } catch (SQLException e) {
            e.getStackTrace();

            bindingResult.rejectValue("id", "중복된 id입니다.");
            System.out.println("직원등록22");
            return "/member/registerMember";

        }


        return "redirect:/login";
    }
}

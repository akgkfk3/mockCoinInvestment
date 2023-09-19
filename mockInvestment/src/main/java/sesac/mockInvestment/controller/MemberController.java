package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;
import sesac.mockInvestment.service.MemberService;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;



    @GetMapping("/member")
    public String signupForm() {
        return "/member/registerMember";
    }

    @PostMapping("/member")
    public String signup(@ModelAttribute RegisterMemberFormDto registerMemberFormDto,
                         BindingResult bindingResult) {
//            @RequestParam("username") String username,

        log.info("DTO {}", registerMemberFormDto.toString());

        try {
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

package sesac.mockInvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sesac.mockInvestment.argumentresolver.Login;
import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class HomeController {


    @GetMapping("/error")
    public String error(HttpServletResponse response) throws IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);

        return "error/4xx";
    }

    @GetMapping("/")
    public String homeLoginArgumentResolver(@Login LoginMemberFormDto LoginMemberFormDto, Model model, HttpSession session) {
//        String deleteMessage = (String) session.getAttribute("deleteMessage");
//        model.addAttribute("deleteMessage", deleteMessage);

        return "index";
    }


}

package sesac.mockInvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/error")
    public String error(HttpServletResponse response) throws IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);

        return null;
    }
}

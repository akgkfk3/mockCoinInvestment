package sesac.mockInvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board")
    public String test() {
        return "board/coinInfo";
    }

    @GetMapping("/read")
    public String test2() {
        return "board/read";
    }

    @GetMapping("/write")
    public String test3() {
        return "board/write";
    }
}

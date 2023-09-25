package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardFormDto;
import sesac.mockInvestment.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     *
     * @param category
     * @return
     */
    @GetMapping("/boards")
    public String list(@RequestParam(name = "category", defaultValue = "free") String category) {
        if (category.equals("news")) {
            return "board/coinNews";
        } else if (category.equals("info")) {
            return "board/coinInfo";
        } else {
            return "board/freeBoard";
        }
    }

    /**
     *
     * @param category
     * @param model
     * @return
     */
    @GetMapping("/board")
    public String writeForm(@ModelAttribute("board") BoardFormDto boardDto, BindingResult bindingResult) {
        if (boardDto.getCategory() == null)
            boardDto.setCategory("free");

        return "board/write";
    }

    /**
     *
     * @return
     */
    @PostMapping("/board")
    public String write(@Validated @ModelAttribute("board") BoardFormDto boardDto, BindingResult bindingResult) {
        // 검증 오류 발생 시
        if (bindingResult.hasErrors()) {
            return "board/write";
        }

        // 성공 로직
        boardService.save(boardDto);


        return "redirect:/";
    }
}

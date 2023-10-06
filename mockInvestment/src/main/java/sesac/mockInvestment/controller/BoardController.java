package sesac.mockInvestment.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sesac.mockInvestment.Exception.NotLoginException;
import sesac.mockInvestment.Exception.RecommandException;
import sesac.mockInvestment.SessionConst;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardEditFormDto;
import sesac.mockInvestment.domain.BoardFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MinioClient minioClient;

    /**
     * (category 파라미터 값에 따른) 게시판 페이지 이동
     * @param String
     * @return String
     * @Author sHu
     */
    @GetMapping("/boards")
    public String list(@RequestParam(name = "category", defaultValue = "free") String category,
                       @RequestParam(required = false) String pageNumber, Model model) {

        // 현재 페이지 번호 (pageNumber)가 없는 경우
        if (pageNumber == null)
            pageNumber = "1";

        int count = boardService.getCount(category);                                // 총 게시글 수
        int currentPage = Integer.parseInt(pageNumber);                             // 현재 페이지 번호
        int boardSize = 5;                                                          // 한 페이지에 보여줄 게시글 개수
        int startRow = (currentPage-1) * boardSize + 1;                             // 현재 페이지 번호의 첫 번째 게시글의 행 번호

        // 게시글 조회
        List<BoardDto> boardList = new ArrayList<>();
        boardList = boardService.getBoards(category, startRow, boardSize);

        // 페이징 처리
        int pageBlock = 4;
        int pageCount = (count / boardSize) + (count % boardSize == 0 ? 0 : 1);

        int result = (currentPage - 1) / pageBlock;
        int startPage = result * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;

        if (endPage > pageCount)
            endPage = pageCount;
        
        // Model에 데이터 저장
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("count", count);

        if (category.equals("news")) {
            return "board/coinNews";
        } else if (category.equals("info")) {
            return "board/coinInfo";
        } else {
            return "board/freeBoard";
        }
    }

    /**
     * 글 쓰기 페이지로 이동
     * @param BoardFormDto
     * @param BindingResult
     * @return String
     * @Author sHu
     */
    @GetMapping("/board")
    public String writeForm(@ModelAttribute("board") BoardFormDto boardDto, BindingResult bindingResult) {
        // 카테고리가 없는 경우 (URL로 다이렉트로 접근한 경우)
        if (boardDto.getCategory() == null)
            boardDto.setCategory("free");
        return "board/write";
    }

    /**
     *
     * @Param BoardFormDto
     * @Param BindingResult
     * @return String
     * @Author sHu
     */
    @PostMapping("/board")
    public String write(@Validated @ModelAttribute("board") BoardFormDto boardDto,
                        BindingResult bindingResult,
                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER) MemberDto memberDto,
                        RedirectAttributes redirectAttributes) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, SQLException {
        // 검증 오류 발생 시
        if (bindingResult.hasErrors()) {
            return "board/write";
        }

        // 카테고리가 없는 경우 (URL로 다이렉트로 접근한 경우)
        if (boardDto.getCategory() == null)
            boardDto.setCategory("free");

        // 등록 날짜/시간 생성
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        // BoardDto 세팅
        BoardDto board = new BoardDto();
        board.setMemberNum(memberDto.getMemberNum());
        board.setCategory(boardDto.getCategory());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setAuthor(memberDto.getId());
        board.setRegisterDate(formatter.format(date));

        // 서비스 로직 호출
        boardService.save(board, boardDto.getFile());

        // 리다이렉트 처리
        redirectAttributes.addAttribute("category", board.getCategory());
        return "redirect:/boards";
    }

    /**
     *
     * @param category
     * @param pageNumber
     * @param boardNum
     * @return
     */
    @GetMapping("/detail/board")
    public String read(@RequestParam String category,
                       @RequestParam Integer boardNum,
                       @RequestParam Integer pageNumber,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                       Model model) {
        BoardDto board = boardService.getBoard(category, boardNum);
        model.addAttribute("board", board);
        model.addAttribute("pageNumber", pageNumber);

        if (memberDto != null && memberDto.getMemberNum().equals(board.getMemberNum()))
            model.addAttribute("boardFlag", true);

        return "board/read";
    }

    @GetMapping("/board/{boardNum}")
    public String editForm(@PathVariable Integer boardNum, Model model) {
        BoardDto board = boardService.editForm(boardNum);

        model.addAttribute("board", board);

        return "board/write";
    }

    @PostMapping("/board/{boardNum}")
    public String edit(@ModelAttribute("board") BoardFormDto boardDto,
                       @PathVariable Integer boardNum,
                       RedirectAttributes redirectAttributes) throws SQLException {

        boardService.edit(boardNum, boardDto);

        return "redirect:/";
    }

    @GetMapping("/detail/board/{boardNum}")
    public String remove(@PathVariable Integer boardNum,
                         @RequestParam Integer pageNumber,
                         @RequestParam String category,
                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto,
                         RedirectAttributes redirectAttributes) {
        // 삭제 로직 실행
        boardService.delete(boardNum, memberDto.getMemberNum());

        redirectAttributes.addAttribute("category", category);
        redirectAttributes.addAttribute("pageNumber", pageNumber);

        return "redirect:/boards";
    }

    @GetMapping("/recommand/{boardNum}")
    @ResponseBody
    public String recommand(@PathVariable Integer boardNum,
                            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDto memberDto) {
        // 로그인이 되지 않은 경우
        if (memberDto == null)
            throw new NotLoginException("로그인을 해주세요.");

        // 추천 로직 실행
        int recommand = boardService.recommand(boardNum, memberDto.getMemberNum());

        return String.valueOf(recommand);
    }

    /**
     *
     * @param path
     * @param response
     */
    @GetMapping("/download/{path}")
    public void download(@PathVariable String path, HttpServletResponse response) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String originalFileName = path.split("_")[1];

        InputStream data = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("akgkfk3")
                        .object(path)
                        .build()
        );

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(data.readAllBytes());
        data.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}

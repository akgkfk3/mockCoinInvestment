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
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardFormDto;
import sesac.mockInvestment.service.BoardService;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

        int count = boardService.getCount();                                        // 총 게시글 수
        int currentPage = Integer.parseInt(pageNumber);                             // 현재 페이지 번호
        int boardSize = 2;                                                          // 한 페이지에 보여줄 게시글 개수
        int startRow = (currentPage-1) * boardSize + 1;                             // 현재 페이지 번호의 첫 번째 게시글의 행 번호

        // 게시글 조회
        List<BoardDto> boardList = new ArrayList<>();
        boardList = boardService.getBoards(category, startRow, boardSize);

        // 페이징 처리
        int pageBlock = 3;                                                          //
        int pageCount = (count / boardSize) + (count % boardSize == 0 ? 0 : 1);     //

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
    public String write(@Validated @ModelAttribute("board") BoardFormDto boardDto, BindingResult bindingResult) {
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
        board.setMemberNum(18);
        board.setCategory(boardDto.getCategory());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setAuthor("akgkfk3");
        board.setRegisterDate(formatter.format(date));

        // 서비스 로직 호출
        boardService.save(board, boardDto.getFile());

        return "redirect:/";
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
                       Model model) {
        BoardDto board = boardService.getBoard(category, boardNum);
        model.addAttribute("board", board);

        return "board/read";
    }

    @GetMapping("/download/{path}")
    public ResponseEntity<InputStream> download(@PathVariable String path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        System.out.println(path);

        // 5-2. 파일 다운로드 (바이너리)
        InputStream response = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("akgkfk3")
                        .object(path)
                        .build()
        );

        return null;
//        return new ResponseEntity<InputStream>(400, response);
    }
}

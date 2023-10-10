package sesac.mockInvestment.controller;

import io.minio.MinioClient;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.service.BoardService;
import sesac.mockInvestment.service.MemberServiceImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final BoardService boardService;
    private final MemberServiceImp memberServiceImp;

    /**
     *
     * @param 관리자 페이지 컨트롤러
     * @param String
     * @return String
     * @throws SQLException
     */
    @GetMapping("/admin")
    public String list(@RequestParam(name = "category", defaultValue = "free") String category,
                       @RequestParam(required = false) String pageNumber, Model model) throws SQLException {

        // 현재 페이지 번호 (pageNumber)가 없는 경우
        if (pageNumber == null)
            pageNumber = "1";

        int count = boardService.getCount(category);                                // 총 게시글 수
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



        // 게시글 조회
        List<BoardDto> freeBoardList = boardService.getBoards("free", startRow, boardSize);
        List<BoardDto> newsBoardList = boardService.getBoards("news", startRow, boardSize);
        List<BoardDto> infoBoardList = boardService.getBoards("info", startRow, boardSize);

        // Model에 데이터 저장
        model.addAttribute("freeBoardList", freeBoardList);
        model.addAttribute("newsBoardList", newsBoardList);
        model.addAttribute("infoBoardList", infoBoardList);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("count", count);

        /////////////////////////////////////////
        /// 멤버 불러오기
        List<MemberDto> members = memberServiceImp.findAll();
        log.info("member List All = {}",members);
        model.addAttribute("members",members);



        return "admin/admin";
    }
}

//@Controller
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final BoardService boardService;
//
//
//
//    @GetMapping("/admin")
//    public String list(@RequestParam(name = "category", defaultValue = "free") String category,
//                       @RequestParam(required = false) String pageNumber, Model model) {
//
//        // 현재 페이지 번호 (pageNumber)가 없는 경우
//        if (pageNumber == null)
//            pageNumber = "1";
//
//        int count = boardService.getCount(category);                                // 총 게시글 수
//        int currentPage = Integer.parseInt(pageNumber);                             // 현재 페이지 번호
//        int boardSize = 2;                                                          // 한 페이지에 보여줄 게시글 개수
//        int startRow = (currentPage-1) * boardSize + 1;                             // 현재 페이지 번호의 첫 번째 게시글의 행 번호
//
//        // 게시글 조회
//        List<BoardDto> boardList = new ArrayList<>();
//        boardList = boardService.getBoards(category, startRow, boardSize);
//
//        // 페이징 처리
//        int pageBlock = 3;                                                          //
//        int pageCount = (count / boardSize) + (count % boardSize == 0 ? 0 : 1);     //
//
//        int result = (currentPage - 1) / pageBlock;
//        int startPage = result * pageBlock + 1;
//        int endPage = startPage + pageBlock - 1;
//
//        if (endPage > pageCount)
//            endPage = pageCount;
//
//        // Model에 데이터 저장
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pageNumber", pageNumber);
//        model.addAttribute("pageBlock", pageBlock);
//        model.addAttribute("pageCount", pageCount);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("count", count);
//
//        return "admin/admin";
////        if (category.equals("news")) {
////            return "board/coinNews";
////        } else if (category.equals("info")) {
////            return "board/coinInfo";
////        } else {
////            return "board/freeBoard";
////        }
//    }
//}

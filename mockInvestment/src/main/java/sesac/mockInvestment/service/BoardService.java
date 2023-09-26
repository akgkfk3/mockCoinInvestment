package sesac.mockInvestment.service;

import org.springframework.web.multipart.MultipartFile;
import sesac.mockInvestment.domain.BoardDto;

import java.util.List;

public interface BoardService {

    BoardDto save(BoardDto boardDto, MultipartFile file);

    List<BoardDto> getBoards(String category, int startRow, int boardSize);

    int getCount(String category);

    BoardDto getBoard(String category, int boardNum);
}

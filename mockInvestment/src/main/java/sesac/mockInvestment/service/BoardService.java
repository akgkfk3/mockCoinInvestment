package sesac.mockInvestment.service;

import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardFormDto;

public interface BoardService {

    BoardDto save(BoardFormDto boardDto);
}

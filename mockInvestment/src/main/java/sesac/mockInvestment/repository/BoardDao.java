package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.BoardDto;

import java.util.List;

public interface BoardDao {

    int save(BoardDto boardDto);

    int getCount(String category);

    List<BoardDto> findByRange(String category, int startRow, int boardSize);

    BoardDto findByNum(String category, int boardNum);

    int save(int boardNum, int memberNum);

    int getRecommand(int boardNum);
}

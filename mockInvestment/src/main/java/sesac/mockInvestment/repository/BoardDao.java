package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardFormDto;

import java.sql.SQLException;
import java.util.List;

public interface BoardDao {

    int save(BoardDto boardDto);

    int delete(int boardNum);

    int getCount(String category);

    BoardDto findByNum(int boardNum);

    List<BoardDto> findByRange(String category, int startRow, int boardSize);

    BoardDto read(String category, int boardNum);

    int save(int boardNum, int memberNum);

    int edit(int boardNum, BoardFormDto boardDto);

    int getRecommand(int boardNum);
}

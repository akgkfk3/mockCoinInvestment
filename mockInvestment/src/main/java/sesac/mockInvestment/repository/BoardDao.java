package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardFormDto;

public interface BoardDao {

    void save(BoardFormDto boardDto);
}

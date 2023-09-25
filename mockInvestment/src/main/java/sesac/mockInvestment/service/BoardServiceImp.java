package sesac.mockInvestment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sesac.mockInvestment.domain.BoardFormDto;
import sesac.mockInvestment.repository.BoardDao;

@Service
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService {

    private final BoardDao boardDao;

    @Override
    public BoardFormDto save(BoardFormDto boardDto) {
        boardDao.save(boardDto);

        return null;
    }
}

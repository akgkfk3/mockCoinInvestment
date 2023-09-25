package sesac.mockInvestment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.repository.BoardDao;
import sesac.mockInvestment.utils.MinioFileStore;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService {

    private final BoardDao boardDao;

    private final MinioFileStore fileStore;

    @Override
    public BoardDto save(BoardDto boardDto, MultipartFile file) {
        // 업로드 해야 할 파일이 있는지 확인
        if (!file.isEmpty()) {
            try {
                fileStore.save(boardDto, file);
            } catch (Exception e) {

            }
        }
        boardDao.save(boardDto);
        return null;
    }

    @Override
    public List<BoardDto> getBoards(String category, int startRow, int boardSize) {
        return boardDao.findByRange(category, startRow, boardSize);
    }

    @Override
    public int getCount() {
        return boardDao.getCount();
    }

    @Override
    public BoardDto getBoard(String category, int boardNum) {
        return boardDao.findByNum(category, boardNum);
    }
}

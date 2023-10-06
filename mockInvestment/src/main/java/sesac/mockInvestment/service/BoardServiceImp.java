package sesac.mockInvestment.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sesac.mockInvestment.Exception.RecommandException;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.domain.BoardFormDto;
import sesac.mockInvestment.repository.BoardDao;
import sesac.mockInvestment.utils.MinioFileStore;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImp implements BoardService {

    private final BoardDao boardDao;

    private final MinioFileStore fileStore;

    @Override
    public void save(BoardDto boardDto, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, SQLException {
        // 업로드 해야 할 파일이 있는지 확인
        if (!file.isEmpty()) {
            fileStore.save(boardDto, file);
        }
        
        // DB에 업데이트가 안된 경우, 예외 발생
        if (boardDao.save(boardDto) != 1) {
            throw new SQLException();
        }
    }

    @Override
    public List<BoardDto> getBoards(String category, int startRow, int boardSize) {
        return boardDao.findByRange(category, startRow, boardSize);
    }

    @Override
    public int getCount(String category) {
        return boardDao.getCount(category);
    }

    @Override
    public BoardDto getBoard(String category, int boardNum) {
        return boardDao.read(category, boardNum);
    }

    @Override
    public int recommand(int boardNum, int memberNum) {
        if (boardDao.save(boardNum, memberNum) != 1) {
            throw new RecommandException("이미 추천하셨습니다.");
        }
        return boardDao.getRecommand(boardNum);
    }

    @Override
    public void edit(int boardNum, BoardFormDto boardDto) throws SQLException {
        // 업로드 해야 할 파일이 있는지 확인
        /*MultipartFile file = boardDto.getFile();
        if (!boardDto.getFile().isEmpty()) {
            fileStore.save(boardDto, file);
        }*/

        // DB에 업데이트가 안된 경우, 예외 발생
        if (boardDao.edit(boardNum, boardDto) != 1) {
            throw new SQLException();
        }
    }

    @Override
    public BoardDto editForm(int boardNum) {
        return boardDao.findByNum(boardNum);
    }

    @Override
    public void delete(int boardNum, int memberNum) {
        BoardDto boardDto = boardDao.findByNum(boardNum);

        if (boardDto == null) {
            throw new RuntimeException();
        } else if (boardDto.getMemberNum() != memberNum) {
            throw new RuntimeException();
        }
        boardDao.delete(boardNum);
    }
}

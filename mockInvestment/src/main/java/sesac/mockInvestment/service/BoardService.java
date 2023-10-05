package sesac.mockInvestment.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import sesac.mockInvestment.domain.BoardDto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface BoardService {

    void save(BoardDto boardDto, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, SQLException;

    List<BoardDto> getBoards(String category, int startRow, int boardSize);

    int getCount(String category);

    BoardDto getBoard(String category, int boardNum);

    int recommand(int boardNum, int memberNum);
}
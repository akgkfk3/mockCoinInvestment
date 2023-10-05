package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.BoardDto;
import sesac.mockInvestment.utils.JdbcUtil;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardDaoImp implements BoardDao {

    private final DataSource dataSource;

    private final JdbcUtil jdbcutil;

    public int save(BoardDto boardDto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            String sql = "INSERT INTO BOARD " +
                         "(MemberNum, Category, Title, Content, Author, Hit, RegisterDate, OriginalFileName, ServerFileName)" +
                         "VALUES" +
                         "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, boardDto.getMemberNum());               // 멤버번호
            pstmt.setString(2, boardDto.getCategory());             // 카테고리
            pstmt.setString(3, boardDto.getTitle());                // 제목
            pstmt.setString(4, boardDto.getContent());              // 내용
            pstmt.setString(5, boardDto.getAuthor());               // 작성자
            pstmt.setInt(6, 0);                                  // 조회수
            pstmt.setString(7, boardDto.getRegisterDate());         // 등록 날짜
            pstmt.setString(8, boardDto.getOriginalFileName());     // 원본 파일명
            pstmt.setString(9, boardDto.getServerFileName());       // 서버 파일명

            count = pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
        } finally {
            jdbcutil.rollback(conn);
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return count;
    }

    @Override
    public List<BoardDto> findByRange(String category, int startRow, int boardSize) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BoardDto> boardList = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM BoardRecommand WHERE Category = ? ORDER BY BoardNum desc LIMIT ? OFFSET ?  ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            pstmt.setInt(2, boardSize);
            pstmt.setInt(3, startRow-1);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                BoardDto board = new BoardDto();
                board.setBoardNum(rs.getInt("BoardNum"));
                board.setCategory(rs.getString("Category"));
                board.setTitle(rs.getString("Title"));
                board.setContent(rs.getString("Content"));
                board.setAuthor(rs.getString("Author"));
                board.setHit(rs.getInt("Hit"));
                board.setRecommand(rs.getInt("Recommand"));
                board.setRegisterDate(rs.getString("RegisterDate"));
                board.setOriginalFileName(rs.getString("OriginalFileName"));
                board.setServerFileName(rs.getString("ServerFileName"));
                boardList.add(board);
            }

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
        } finally {
            jdbcutil.close(rs);
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return boardList;
    }

    @Override
    public BoardDto findByNum(String category, int boardNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardDto board = new BoardDto();

        try {
            conn = dataSource.getConnection();

            String sql = "UPDATE BOARD SET Hit = Hit + 1 WHERE Category = ? AND BoardNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            pstmt.setInt(2, boardNum);

            // 조회수 업데이트 (+1)
            pstmt.executeUpdate();

            // 게시글 검색
            sql = "SELECT * FROM BoardRecommand WHERE Category = ? AND BoardNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            pstmt.setInt(2, boardNum);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                board.setBoardNum(rs.getInt("BoardNum"));
                board.setMemberNum(rs.getInt("MemberNum"));
                board.setCategory(rs.getString("Category"));
                board.setTitle(rs.getString("Title"));
                board.setContent(rs.getString("Content"));
                board.setAuthor(rs.getString("Author"));
                board.setHit(rs.getInt("Hit"));
                board.setRecommand(rs.getInt("Recommand"));
                board.setRegisterDate(rs.getString("RegisterDate"));
                board.setOriginalFileName(rs.getString("OriginalFileName"));
                board.setServerFileName(rs.getString("ServerFileName"));
            }
            conn.commit();

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
            jdbcutil.rollback(conn);
        } finally {
            jdbcutil.close(rs);
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return board;
    }

    @Override
    public int save(int boardNum, int memberNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            conn = dataSource.getConnection();

            String sql = "INSERT INTO RECOMMAND " +
                    "(MemberNum, BoardNum)" +
                    "VALUES" +
                    "(?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberNum);
            pstmt.setInt(2, boardNum);

            count = pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
            jdbcutil.rollback(conn);
        } finally {
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return count;
    }

    @Override
    public int getRecommand(int boardNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT COUNT(*) FROM RECOMMAND WHERE BoardNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardNum);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
        } finally {
            jdbcutil.close(rs);
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return count;
    }

    @Override
    public int getCount(String category) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = dataSource.getConnection();

            String sql = "SELECT COUNT(*) FROM BOARD WHERE Category = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
        } finally {
            jdbcutil.close(rs);
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return count;
    }
}

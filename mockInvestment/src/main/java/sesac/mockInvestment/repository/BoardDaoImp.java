package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.BoardFormDto;
import sesac.mockInvestment.utils.JdbcUtil;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardDaoImp implements BoardDao {

    private final DataSource dataSource;

    private final JdbcUtil jdbcutil;

    public void save(BoardFormDto boardDto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            // 그룹번호
            // 그룹번호 가져오기
            // select * from board orderby groupNum desc limit 1 for update;
            // select * from board orderby groupnum desc limit 1 for share;

            String sql = "INSERT INTO BOARD " +
                         "(Category, Title, Content, Hit, RegisterDate, MemberNum, Author, GroupNum)" +
                         "VALUES" +
                         "(?, ?, ?, ?, ?, ?, ?, ?)";
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, boardDto.getCategory());
            pstmt.setString(2, boardDto.getTitle());
            pstmt.setString(3, boardDto.getContent());
            pstmt.setInt(4, 0);
            pstmt.setString(5, "1995-06-22");
            pstmt.setInt(6, 18);
            pstmt.setString(7, "akgkfk3");
            pstmt.setInt(8, 3);

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
        } finally {
            jdbcutil.rollback(conn);
            jdbcutil.close(rs);
            jdbcutil.close(pstmt);
        }
    }

    public int getMaxCount() {


        return 0;
    }
}

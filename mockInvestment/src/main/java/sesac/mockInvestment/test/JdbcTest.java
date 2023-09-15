package sesac.mockInvestment.test;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest {

    public static HikariDataSource dataSource = new HikariDataSource();

    static {
        // 접속 정보
        String url = "";
        String id = "";
        String pwd = "";

        // 스레드풀 설정
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(id);
        dataSource.setPassword(pwd);
        dataSource.setMaximumPoolSize(100);
        dataSource.setPoolName("test");

    }

    public static void main(String[] args) {
        try {
            Connection conn = dataSource.getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from MEMBER");

            while(rs.next()) {
                System.out.println(rs.getInt(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error 발생!!");
        }
    }
}

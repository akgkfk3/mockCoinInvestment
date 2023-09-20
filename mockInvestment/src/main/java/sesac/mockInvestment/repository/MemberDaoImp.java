package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDaoImp implements MemberDao {

    private final DataSource dataSource;

    @Override
    public MemberDto select(MemberDto memberDto) throws SQLException {

        Connection conn = dataSource.getConnection();

        String sql = "select * from MEMBERS where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, memberDto.getId());

//        ResultSet result = pstmt.executeQuery();
//        //execute : execute(anySQL-callableSQL), executeUpdate(otherSQL), executeQuery(selectSQL)
//
//        while(result.next()){
//            System.out.println("while rr");
//        }
        System.out.println("memberDto"+memberDto);

//        result.close();
        pstmt.close();
        conn.close();
        return memberDto;

    }

    @Override
    public int save(RegisterMemberFormDto memberDto) throws SQLException {

//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = dataSource.getConnection();

//        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);


        String sql = "INSERT INTO MEMBERS (ID, Password, Name, BirthDate, Gender, PhoneNumber, Email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, memberDto.getId());
        pstmt.setString(2, memberDto.getPassword1());
        pstmt.setString(3, memberDto.getName());
//        pstmt.setDate(4, new java.sql.Date(memberDto.getBirthDate().getTime()));
        pstmt.setDate(4, (Date) memberDto.getBirthDate());
        pstmt.setString(5, String.valueOf(memberDto.getGender()));
        pstmt.setString(6, memberDto.getPhoneNumber());
        pstmt.setString(7, memberDto.getEmail());


        int i = pstmt.executeUpdate();
        if(i==1){
            conn.commit();
        }else {
            conn.rollback();
        }
        log.info("result {}", i);


        pstmt.close();
        conn.close();

        return i;
    }

    @Override
    public int delete(RegisterMemberFormDto memberDto) throws SQLException {
        return 0;
    }
}

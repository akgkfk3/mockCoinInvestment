package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDaoImp implements MemberDao {

    private final DataSource dataSource;


    @Override
    public Optional<MemberDto> findById(String id) throws SQLException {
        System.out.println(findAll().stream().filter(m-> m.getId().equals(id)).findFirst());
        System.out.println("selectby test");
        return findAll().stream().filter(m-> m.getId().equals(id)).findFirst();
    }

    @Override
    public ArrayList<MemberDto> findAll() throws SQLException {
        Connection conn = dataSource.getConnection();

        String sql = "select * from MEMBERS";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet result = pstmt.executeQuery();

        MemberDto member = null;
        ArrayList<MemberDto> members = new ArrayList<MemberDto>();

        while (result.next()) {
            member  = new MemberDto(
                    result.getString("id"),
                    result.getString("password"),
                    result.getString("name"),
                    result.getDate("birthDate"),
                    result.getString("gender"),
                    result.getString("phoneNumber"),
                    result.getString("email"));
            members.add(member);
        }
        System.out.println(members);
        result.close();
        pstmt.close();
        conn.close();
        return members;
    }

    @Override
    public int save(RegisterMemberFormDto memberDto) throws SQLException {
        System.out.println("save 접속1111");
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = dataSource.getConnection();

//        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//        formatter.parse(memberDto.getBirthDate());
        System.out.println("getBirthDate = " + memberDto.getBirthDate());

//        java.util.Date utilBirthDate = memberDto.getBirthDate();
//        System.out.println("utilBirthDate"+utilBirthDate);
//        java.sql.Date sqlBirthDate = utilBirthDate != null ? new java.sql.Date(utilBirthDate.getTime()) : new java.sql.Date(System.currentTimeMillis());
//        System.out.println("sqlBirthDate"+sqlBirthDate);

        String sql = "INSERT INTO MEMBERS (ID, Password, Name, BirthDate, Gender, PhoneNumber, Email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, memberDto.getId());
        pstmt.setString(2, memberDto.getPassword1());
        pstmt.setString(3, memberDto.getName());
//        pstmt.setDate(4, new java.sql.Date(memberDto.getBirthdate().getTime()));
        pstmt.setDate(4, Date.valueOf(memberDto.getBirthDate()));
        pstmt.setString(5, memberDto.getGender());
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

package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDaoImp implements MemberDao {

    private final DataSource dataSource;

    @Override
    public MemberDto findById(String id) throws SQLException {
        System.out.println("findByID 접근");
        Connection conn = dataSource.getConnection();
        String sql = "select * from MEMBERS where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();

        MemberDto memberDto = null;
        if (rs.next()) {
            memberDto = new MemberDto();
            memberDto.setId(rs.getString("id"));
            memberDto.setPassword(rs.getString("password"));
            memberDto.setName(rs.getString("name"));
            memberDto.setBirthDate(rs.getDate("birthDate"));
            memberDto.setGender(rs.getString("gender"));
            memberDto.setPhoneNumber(rs.getString("phoneNumber"));
            memberDto.setEmail(rs.getString("email"));
        }
        System.out.println(memberDto+"memberDTO 체크");

        close(conn, pstmt, rs);

        return memberDto;
    }

    @Override
    public ArrayList<MemberDto> findAll() throws SQLException {
        Connection conn = dataSource.getConnection();

        String sql = "select * from MEMBERS";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        MemberDto member = null;
        ArrayList<MemberDto> members = new ArrayList<>();

        while (rs.next()) {
            member  = new MemberDto(
                    rs.getString("id"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getDate("birthDate"),
                    rs.getString("gender"),
                    rs.getString("phoneNumber"),
                    rs.getString("email"));
            members.add(member);
        }
        System.out.println(members);
        close(conn,pstmt,rs);
        return members;
    }

    @Override
    public int save(RegisterMemberFormDto memberDto) throws SQLException {
        System.out.println("save 접속1111");
        Connection conn = dataSource.getConnection();

        String sql = "INSERT INTO MEMBERS (ID, Password, Name, BirthDate, Gender, PhoneNumber, Email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // 비밀번호 암호화 하기
        String rawPassword = memberDto.getPassword1(); // 사용자로부터 입력 받은 패스워드

        // PasswordEncoder를 생성 (Bcrypt 사용)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 랜덤 솔트와 함께 패스워드 해싱
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // 해싱된 패스워드 출력
        System.out.println("해싱된 패스워드: " + hashedPassword);



        pstmt.setString(1, memberDto.getId());
        pstmt.setString(2, hashedPassword);
        pstmt.setString(3, memberDto.getName());
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


        close(conn,pstmt,null);


        return i;
    }


//    @Override
//    public int findByIdDelete(String id, String password) throws SQLException{
//        System.out.println("findByIdDelete 접근");
//        Connection conn = dataSource.getConnection();
//        String sql = "select * from MEMBERS where id = ? and password = ?";
//
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1, id);
//        pstmt.setString(2, password);

//        memberService.login(loginMemberFormDto.getId(), loginMemberFormDto.getPassword())
//
//        int result = pstmt.executeUpdate();
//
//        if (memberDto == null || !memberDto.getPassword().equals(hashedPassword)) {
//            return null;
//        }
//
//        MemberDto memberDto = null;
//        if (rs.next()) {
//            memberDto = new MemberDto();
//            memberDto.setId(rs.getString("id"));
//            memberDto.setPassword(rs.getString("password"));
//            memberDto.setName(rs.getString("name"));
//            memberDto.setBirthDate(rs.getDate("birthDate"));
//            memberDto.setGender(rs.getString("gender"));
//            memberDto.setPhoneNumber(rs.getString("phoneNumber"));
//            memberDto.setEmail(rs.getString("email"));
//        }
//        System.out.println(memberDto+"memberDTO 체크");
//
//        close(conn, pstmt, rs);

//        return result;
//        return 1;
//    }
    @Override
    public int delete(String id) throws SQLException {
        String sql = "delete from member where id=?";
        Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        int result = pstmt.executeUpdate();

        close(conn,pstmt,null);
        return result;
    }

    public int update(String id, MemberDto memberDto) throws SQLException{

            String sql = "update MEMBERS set name=?, birthDate=?, gender=?, phoneNumber=?, email=? where member_id=?";

            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberDto.getName()); // name
            pstmt.setDate(2, (Date) memberDto.getBirthDate()); // birthDate
            pstmt.setString(3, memberDto.getGender()); // gender
            pstmt.setString(4, memberDto.getPhoneNumber()); // phoneNumber
            pstmt.setString(5, memberDto.getEmail()); // email
            pstmt.setString(6, id); // id

            int result = pstmt.executeUpdate();



            close(conn,pstmt,null);

            return result;
    }

    @Override
    public void close(Connection conn, Statement stmt, ResultSet resultSet) {
        if(resultSet != null){
            try {
                resultSet.close();
            }catch (SQLException e){
                log.info("error",e);
            }
        }

        if (stmt != null){
            try {
                stmt.close();
            }catch (SQLException e){
                log.info("error",e);
            }
        }

        if (conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                log.info("error",e);
            }
        }



    }
}

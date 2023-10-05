package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.EditMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;
import sesac.mockInvestment.utils.JdbcUtil;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDaoImp implements MemberDao {

    private final DataSource dataSource;

    private final JdbcUtil jdbcUtil;

    @Override
    public MemberDto findById(String id) {
        System.out.println("findByID 접근");
        String sql = "select * from MEMBERS where id = ?";
        Connection conn =null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        MemberDto memberDto = null;
        try {
            conn= dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memberDto = new MemberDto();
                memberDto.setMemberNum(rs.getInt("MemberNum"));
                memberDto.setId(rs.getString("id"));
                memberDto.setPassword(rs.getString("password"));
                memberDto.setName(rs.getString("name"));
                memberDto.setBirthDate(rs.getDate("birthDate"));
                memberDto.setGender(rs.getString("gender"));
                memberDto.setPhoneNumber(rs.getString("phoneNumber"));
                memberDto.setEmail(rs.getString("email"));
            }
            System.out.println(memberDto+"memberDTO 체크");

        } catch (SQLException e){
            e.getStackTrace();
            jdbcUtil.rollback(conn);

        } finally {
            jdbcUtil.close(conn);
            jdbcUtil.close(rs);
            jdbcUtil.close(pstmt);
        }
        return memberDto;
    }

    @Override
    public ArrayList<MemberDto> findAll(){
        Connection conn =null;

        String sql = "select * from MEMBERS";
        PreparedStatement pstmt =null;
        ResultSet rs =null;

        MemberDto member = null;
        ArrayList<MemberDto> members = new ArrayList<>();
        try{
            conn = dataSource.getConnection();
            pstmt= conn.prepareStatement(sql);
            rs =  pstmt.executeQuery();

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

        } catch (SQLException e){
            e.getStackTrace();
            jdbcUtil.rollback(conn);
        } finally {
            jdbcUtil.close(conn);
            jdbcUtil.close(rs);
            jdbcUtil.close(pstmt);
        }
        return members;
    }

    @Override
    public int save(RegisterMemberFormDto memberDto) {
        System.out.println("save 접속1111");
        Connection conn = null;

        String sql = "INSERT INTO MEMBERS (ID, Password, Name, BirthDate, Gender, PhoneNumber, Email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        
        // 비밀번호 암호화 하기
        String rawPassword = memberDto.getPassword1(); // 사용자로부터 입력 받은 패스워드

        // PasswordEncoder를 생성 (Bcrypt 사용)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 랜덤 솔트와 함께 패스워드 해싱
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // 해싱된 패스워드 출력
        System.out.println("해싱된 패스워드: " + hashedPassword);

        int result =0;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, memberDto.getId());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, memberDto.getName());
            pstmt.setDate(4, Date.valueOf(memberDto.getBirthDate()));
            pstmt.setString(5, memberDto.getGender().toString());
            pstmt.setString(6, memberDto.getPhoneNumber());
            pstmt.setString(7, memberDto.getEmail());


            result = pstmt.executeUpdate();
            if(result==1){
                conn.commit();
            }else {
                conn.rollback();
            }
            log.info("result {}", result);
        }catch (SQLException e){
            e.getStackTrace();
            jdbcUtil.rollback(conn);
        }
        finally {
            jdbcUtil.close(conn);
            jdbcUtil.close(pstmt);
        }
        return result;
    }

    @Override
    public int delete(String id)  {
        String sql = "DELETE FROM MEMBERS WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn =dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            result = pstmt.executeUpdate();
            if(result==1){
                conn.commit();
            }else {
                conn.rollback();
            }

        } catch (SQLException e){
            e.getStackTrace();
            jdbcUtil.rollback(conn);
        } finally {
            jdbcUtil.close(conn);
            jdbcUtil.close(pstmt);
        }
        return result;
    }

    public int update(String id, EditMemberFormDto memberDto){

        String sql = "update MEMBERS set password = ? where id=?";

        // 비밀번호 암호화 하기
        String rawPassword = memberDto.getPassword1(); // 사용자로부터 입력 받은 패스워드

        // PasswordEncoder를 생성 (Bcrypt 사용)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 랜덤 솔트와 함께 패스워드 해싱
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // 해싱된 패스워드 출력
        System.out.println("업데이트를 위한 해싱된 패스워드: " + hashedPassword);

            Connection conn = null;
            PreparedStatement pstmt = null;
            int result = 0;
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, id); // id

            result = pstmt.executeUpdate();

            if(result==1){
                conn.commit();
            }else {
                conn.rollback();
            }


        } catch (SQLException e){
            e.getStackTrace();
            jdbcUtil.rollback(conn);
        } finally {
            jdbcUtil.close(conn);
            jdbcUtil.close(pstmt);
        }
        return result;
    }
}

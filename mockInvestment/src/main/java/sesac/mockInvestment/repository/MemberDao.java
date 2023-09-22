package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface MemberDao {

    MemberDto findById(String id) throws SQLException;
    ArrayList<MemberDto> findAll() throws SQLException;

    int save(RegisterMemberFormDto memberDto) throws SQLException;
    int delete(String id) throws SQLException;

    void close(Connection conn, Statement stmt, ResultSet resultSet);

}

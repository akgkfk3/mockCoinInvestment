package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.EditMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public interface MemberDao {

    MemberDto findById(String id);
    ArrayList<MemberDto> findAll();

    int save(RegisterMemberFormDto memberDto);
    int update(String id, EditMemberFormDto memberDto);
    int delete(String id);

    void close(Statement stmt, ResultSet resultSet);

}

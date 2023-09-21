package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface MemberDao {

    Optional<MemberDto> findById(String id) throws SQLException;
    ArrayList<MemberDto> findAll() throws SQLException;

    int save(RegisterMemberFormDto memberDto) throws SQLException;
    int delete(RegisterMemberFormDto memberDto) throws SQLException;
}

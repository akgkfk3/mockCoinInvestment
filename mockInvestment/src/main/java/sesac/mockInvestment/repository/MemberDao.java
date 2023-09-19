package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import java.sql.SQLException;

public interface MemberDao {

    MemberDto select(MemberDto memberDto) throws SQLException;
    int save(RegisterMemberFormDto memberDto) throws SQLException;
    int delete(RegisterMemberFormDto memberDto) throws SQLException;
}

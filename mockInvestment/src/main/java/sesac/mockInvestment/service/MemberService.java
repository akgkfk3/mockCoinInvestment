package sesac.mockInvestment.service;

import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import java.sql.SQLException;

public interface MemberService {

//    MemberDto getBoard(MemberDto memberDto);

    MemberDto select(MemberDto memberDto) throws SQLException;
    String save(RegisterMemberFormDto memberDto) throws SQLException;
}

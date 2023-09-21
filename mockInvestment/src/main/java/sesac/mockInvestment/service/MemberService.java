package sesac.mockInvestment.service;

import sesac.mockInvestment.domain.LoginMemberFormDto;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MemberService {

//    MemberDto getBoard(MemberDto memberDto);

//    Optional<LoginMemberFormDto> selectById(LoginMemberFormDto LoginMemberFormDto) throws SQLException;
    Optional<MemberDto> selectById(String memberId) throws SQLException;

    List<MemberDto> selectAll() throws SQLException;
    String save(RegisterMemberFormDto memberDto) throws SQLException;
}

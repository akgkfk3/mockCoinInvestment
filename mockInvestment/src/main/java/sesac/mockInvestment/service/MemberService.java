package sesac.mockInvestment.service;

import sesac.mockInvestment.domain.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MemberService {

//    MemberDto getBoard(MemberDto memberDto);

//    Optional<LoginMemberFormDto> selectById(LoginMemberFormDto LoginMemberFormDto) throws SQLException;
    Optional<MemberDto> selectById(String memberId) throws SQLException;

    List<MemberDto> selectAll() throws SQLException;
    String save(RegisterMemberFormDto memberDto) throws SQLException;

    String update(EditMemberFormDto memberDto);

    String delete(DeleteMemberFormDto memberDto);

    MemberDto login(String id, String password);

    boolean isIdDuplicated(String id) throws SQLException;
}

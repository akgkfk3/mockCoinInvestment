package sesac.mockInvestment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;
import sesac.mockInvestment.repository.MemberDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {

    private final MemberDao memberDao;
    private String result;


//    @Override
//    public Optional<LoginMemberFormDto> selectById(LoginMemberFormDto LoginMemberFormDto) throws SQLException {
//        Optional<sesac.mockInvestment.domain.LoginMemberFormDto> result = memberDao.selectById(String.valueOf(LoginMemberFormDto));
//        return result;
//    }

    @Override
    public Optional<MemberDto> selectById(String memberId) throws SQLException {


//        MemberDto result = memberDao.selectById(memberId);
//        if (result != null) {
//            MemberDto memberDto = new MemberDto();
//            memberDto.setId(memberDto.getId());
//            memberDto.setPassword(memberDto.getPassword());
//            // 필요한 다른 정보도 매핑
//
//            return memberDto;
//        } else {
//            return null; // 회원이 존재하지 않을 경우 null 반환
//        }
        return null;
    }

    @Override
    public List<MemberDto> selectAll() throws SQLException {
        List<MemberDto> result = memberDao.findAll();
        return result;
    }

    @Override
    public String save(RegisterMemberFormDto memberDto) throws SQLException {

       int result = memberDao.save(memberDto);

       if(result >= 1){
           this.result = "직원 등록 완료";
           System.out.println("직원등록 성공");
       } else{
           this.result = "직원 등록 실패";
           System.out.println("직원등록 실패");
       }

        return this.result;
    }


}

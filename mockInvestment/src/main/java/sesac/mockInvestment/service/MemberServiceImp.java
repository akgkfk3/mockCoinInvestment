package sesac.mockInvestment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.domain.RegisterMemberFormDto;
import sesac.mockInvestment.repository.MemberDao;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {

    private final MemberDao memberDao;
    private String result;

    @Override
    public MemberDto select(MemberDto memberDto) throws SQLException {
        return memberDao.select(memberDto);
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

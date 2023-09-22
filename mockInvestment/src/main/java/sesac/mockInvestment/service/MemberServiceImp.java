package sesac.mockInvestment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Override
    public MemberDto login(String id, String password) throws SQLException {

        MemberDto memberDto = memberDao.findById(id);
//
//        String rawPassword = password; // 사용자로부터 입력 받은 패스워드
//
//        // PasswordEncoder를 생성 (Bcrypt 사용)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        // 랜덤 솔트와 함께 패스워드 해싱
//        String hashedPassword = passwordEncoder.encode(rawPassword);
//
//        // 해싱된 패스워드 출력
//        System.out.println("해싱된 패스워드: " + hashedPassword);

        // 패스워드 검증 예제
        boolean passwordMatches = passwordEncoder.matches(password, memberDto.getPassword());
        System.out.println("패스워드 검증 결과: " + passwordMatches);


        // 회원을 찾지 못하거나 비밀번호가 일치하지 않는 경우 null 반환
        if (!passwordMatches) {
            return null;
        }

        return memberDto;
//        return memberDao.findById(id)
//                .filter(m -> m.getPassword().equals(hashedPassword))
//                .orElse(null);
    }

    @Override
    public boolean isIdDuplicated(String id) throws SQLException {
        // 아이디 중복 체크를 위해 MemberRepository를 사용하여 아이디로 회원을 조회합니다.
        MemberDto existingMember = memberDao.findById(id);
        // 중복되지 않으면 existingMember는 null이며, 중복되면 existingMember에 값이 채워집니다.
        return existingMember != null;
    }
}

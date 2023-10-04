package sesac.mockInvestment.service;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sesac.mockInvestment.domain.DeleteMemberFormDto;
import sesac.mockInvestment.domain.EditMemberFormDto;
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
    private final MinioClient minioClient;
    private String result;

    @Override
    public Optional<MemberDto> selectById(String memberId) throws SQLException {

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
    public String update(EditMemberFormDto memberDto) {


        int result = memberDao.update(memberDto.getId(), memberDto);

        if(result>=1){
            this.result = "업데이트 성공";

        }else{
            this.result ="업데이트 실패";
        }

        return this.result;
    }

    @Override
    public String delete(DeleteMemberFormDto memberDto) {
//        MemberDto memberDto1 = memberDao.findById(memberDto.getId());
        int result = memberDao.delete(memberDto.getId());

        if(result>=1){
            this.result = "삭제 완료";
        }else {
            this.result = "삭제 실패";
        }

        return null;
    }


    @Override
    public MemberDto login(String id, String password)  {

        MemberDto memberDto = memberDao.findById(id);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 패스워드 검증 예제
        boolean passwordMatches = passwordEncoder.matches(password, memberDto.getPassword());
        System.out.println("패스워드 검증 결과: " + passwordMatches);


        // 회원을 찾지 못하거나 비밀번호가 일치하지 않는 경우 null 반환
        if (!passwordMatches) {
            return null;
        }

        return memberDto;
    }

    @Override
    public boolean isIdDuplicated(String id) throws SQLException {
        // 아이디 중복 체크를 위해 MemberRepository를 사용하여 아이디로 회원을 조회합니다.
        MemberDto existingMember = memberDao.findById(id);
        // 중복되지 않으면 existingMember는 null이며, 중복되면 existingMember에 값이 채워집니다.
        return existingMember != null;
    }

    public void uploadFile(String bucketName, String objectName, MultipartFile file) {
//        try (InputStream inputStream = file.getInputStream()) {
//
//            minioClient.putObject(bucketName, objectName, inputStream, file.getSize(), file.getContentType())
//        } catch (Exception e) {
//            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
//        }
    }
}

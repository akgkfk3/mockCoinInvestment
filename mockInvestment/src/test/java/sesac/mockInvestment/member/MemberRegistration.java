package sesac.mockInvestment.member;

import org.junit.jupiter.api.Test;
import sesac.mockInvestment.domain.MemberDto;
import sesac.mockInvestment.repository.MemberDao;
import sesac.mockInvestment.repository.MemberDaoImp;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

//@Slf4j
public class MemberRegistration {

//    private final DataSource dataSource;

//    MemberDao memberDao = new MemberDaoImp(datasource);
//


    @Test
    void crud() throws SQLException{
        MemberDto memberDto = new MemberDto("testtest","testtest","1999-11-11","m","010-1234-5678","abc@abc.com");

//        memberDao.save(memberDto.getId());

//        MemberDto findMember = memberDao.findById(memberDto.getMemberId());
//        log.info("findMember={}", findMember);
//        assertThat(findMember).isEqualTo(memberDto);
    }
}

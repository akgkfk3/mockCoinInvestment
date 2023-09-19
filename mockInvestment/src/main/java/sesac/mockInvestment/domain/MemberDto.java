package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MemberDto {
    private int memberNum;
    private String id;
    private String password;
    private String name;
    private Date birthDate;
    private char gender;
    private String phoneNumber;
    private String email;
    private Date registerDate;
    private long money;
    private char grade;
}

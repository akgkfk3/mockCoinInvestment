package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private String gender;
    private String phoneNumber;
    private String email;
    private Date registerDate;
    private long money;
    private char grade;

    public MemberDto(String id, String password, String name, java.sql.Date birthDate, String gender, String phoneNumber, String email) {
    }
}

package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterMemberFormDto {
    private String id;
    private String password;
    private String name;
    private Date birthDate;
    private char gender;
    private String phoneNumber;
    private String email;
}

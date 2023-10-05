package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer memberNum;
    private String id;
    private String password;
    private String name;
    private Date birthDate;
    private String gender;
    private String phoneNumber;
    private String email;
    private Date registerDate;
    private Long money;
    private Character grade;

    public MemberDto(String id, String password, String name, java.sql.Date birthDate, String gender, String phoneNumber, String email) {
    }
    public MemberDto(){}

    public MemberDto(String testtest, String testtest1, String date, String m, String s, String mail) {
    }
}

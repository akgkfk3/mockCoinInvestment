package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoginMemberFormDto {

    @NotBlank
    private String id;

//    @NotBlank
//    @Pattern()
    private String password;
}

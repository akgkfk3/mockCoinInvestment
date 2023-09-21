package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoginMemberFormDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @NotNull(message = "아이디를 맞게 입력했는지 확인해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @NotNull(message = "비밀번호를 맞게 입력했는지 확인해주세요.")
    private String password;
}

package sesac.mockInvestment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.sql.Date;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterMemberFormDto {


    @NotNull(message = "ID는 null일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}$", message = "ID는 최소2자에서 10자까지 허용되며, 알파벳 대문자, 소문자, 숫자만 허용됩니다.")
    private String id;

    @NotNull(message = "비밀번호는 null일 수 없습니다.")
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{4,18}$", message = "비밀번호에 소문자, 대문자, 특수문자 4~18자 사이를 포함해주세요.")
    private String password;

    @NotNull(message = "이름은 null일 수 없습니다.")
    @Pattern(regexp ="^[a-zA-Z가-힣]*$", message = "이름은 영문자 및 한글만 가능합니다.")
    private String name;

    @NotNull(message = "태어난 일시를 yyyy-MM-dd 형식으로 적어주세요.")
    @Past(message = "생일은 과거 날짜여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "성별을 골라주세요. (Male, Female)")
    private String gender;

    @NotNull(message = "폰 번호를 010-1234-4567 형식으로 적어주세요.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "ex) 010-1111-2222 형식으로 작성해주세요.")
    private String phoneNumber;

    @NotNull(message = "Email 주소를 abc@abc.com 형식으로 적어주세요.")
    @Email(message = "Email 주소를 abc@abc.com 형식으로 적어주세요.")
    private String email;
}

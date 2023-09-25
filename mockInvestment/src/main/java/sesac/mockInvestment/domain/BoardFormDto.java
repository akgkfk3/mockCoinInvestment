package sesac.mockInvestment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class BoardFormDto {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(info|news|free)$", message = "카테고리가 틀렸습니다.")
    private String category;

    @NotNull
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull
    private String content;

    private MultipartFile file;
}

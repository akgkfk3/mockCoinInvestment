package sesac.mockInvestment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class BoardEditFormDto {
    @NotNull
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull
    private String content;

    private MultipartFile file;
}

package sesac.mockInvestment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Getter
@Setter
public class BoardDto {

    private Integer boardNum;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(info|news|free)$", message = "카테고리가 틀렸습니다.")
    private String category;

    @NotNull
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String author;

    private Integer hit;

    private Date registerDate;

    private String originalFileName;

    private String serverFileName;

}

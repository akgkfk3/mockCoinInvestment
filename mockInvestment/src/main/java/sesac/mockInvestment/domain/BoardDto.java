package sesac.mockInvestment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class BoardDto {

    private Integer boardNum;

    private Integer memberNum;

    private String category;

    private String title;

    private String content;

    private String author;

    private Integer hit;

    private Integer recommand;

    private String registerDate;

    private String originalFileName;

    private String serverFileName;
}

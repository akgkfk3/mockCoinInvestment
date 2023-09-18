package sesac.batchAndAlarm.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CoinDto {
    private String name;

    private Double curPrice;

    private Double CompareToDay;

    private Double tradeMoney;
}

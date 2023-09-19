package sesac.batchAndAlarm.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CoinDto {
    private String market;                      // 마켓

    private String name;                        // 코인 이름

    private Double opening_price;               // 금일 시작가

    private Double low_price;                   // 금일 최저가

    private Double high_price;                  // 금일 최고가

    private Double trade_price;                 // 금일 현재가

    private Double prev_closing_price;          // 전일 종가

    private Double acc_trade_price_24h;         // 거래 대금
}

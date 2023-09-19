package sesac.batchAndAlarm.scheduler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sesac.batchAndAlarm.domain.CoinDto;
import sesac.batchAndAlarm.repository.CoinRepository;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class CoinScheduler {

    private final CoinRepository coinRepository;

    /**
     * 업비트 API를 통해 받은 코인 정보를 10초마다 DB에 업데이트
     * @Author 박성수
     * @Param  없음
     * @Return 없음
     * @throws SQLException
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void updateCoin() {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // 업비트 API의 엔드포인트 URL (마켓별 코인 전체 목록)
        String apiUrl = "https://api.upbit.com/v1/market/all";
        MarketCoinDto[] coinArr = restTemplate.getForObject(apiUrl, MarketCoinDto[].class);

        // 비트코인이라 하더라도 KRW, BTC 등 마켓별로 동일한 코인이 존재하기 때문에 원화 마켓에 한정해서
        // 데이터를 조회하기 위해 URL 수정
        apiUrl = "https://api.upbit.com/v1/ticker?markets=";
        Map<String, String> coinNameMap = new HashMap<>();

        for (MarketCoinDto marketCoinDto : coinArr) {
            if (marketCoinDto.getMarket().startsWith("KRW")) {
                apiUrl += marketCoinDto.getMarket() + ",";
                coinNameMap.put(marketCoinDto.getMarket(), marketCoinDto.getKorean_name());
            }
        }
        apiUrl = apiUrl.substring(0, apiUrl.length() - 1);

        // 업비트 API의 엔드포인트 URL (원화 코인 전체 목록)
        CoinDto[] coinList = restTemplate.getForObject(apiUrl, CoinDto[].class);

        Arrays.stream(coinList).forEach((coin) -> {
            coin.setName(coinNameMap.get(coin.getMarket()));
        });

        // 코인정보 업데이트 to DB
        coinRepository.updateAllCoin(new ArrayList<>(List.of(coinList)));

        System.out.println("업데이트 완료");
    }

    @Getter
    @Setter
    static class MarketCoinDto {
        private String market;
        private String korean_name;
    }
}

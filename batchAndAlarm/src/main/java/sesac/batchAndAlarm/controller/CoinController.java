package sesac.batchAndAlarm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sesac.batchAndAlarm.domain.CoinDto;
import sesac.batchAndAlarm.service.CoinService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/coins")
    public List<CoinDto> getCoinList() {
        return coinService.getAllCoin();
    }
}

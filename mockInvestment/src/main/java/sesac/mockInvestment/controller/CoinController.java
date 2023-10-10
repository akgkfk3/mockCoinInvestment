package sesac.mockInvestment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sesac.mockInvestment.domain.CoinDto;
import sesac.mockInvestment.service.CoinService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CoinController {

    private final CoinService coinService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coinList")
    public List<CoinDto> getAllCoin() {
        return coinService.getCoinList();
    }
}

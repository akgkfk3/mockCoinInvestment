package sesac.mockInvestment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sesac.mockInvestment.domain.CoinDto;
import sesac.mockInvestment.repository.CoinDao;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinServiceImp implements CoinService{

    private final CoinDao coinDao;

    @Override
    public List<CoinDto> getCoinList() {
        return coinDao.getCoinList();
    }
}

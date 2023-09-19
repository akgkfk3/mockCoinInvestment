package sesac.batchAndAlarm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sesac.batchAndAlarm.domain.CoinDto;
import sesac.batchAndAlarm.repository.CoinRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinServiceImp implements CoinService {

    private final CoinRepository coinRepository;

    @Override
    public List<CoinDto> getAllCoin() {
        return coinRepository.getCoinList();
    }
}

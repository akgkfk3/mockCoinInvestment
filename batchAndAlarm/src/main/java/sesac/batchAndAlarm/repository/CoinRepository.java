package sesac.batchAndAlarm.repository;

import sesac.batchAndAlarm.domain.CoinDto;

import java.util.ArrayList;
import java.util.List;

public interface CoinRepository {

    void updateAllCoin(ArrayList<CoinDto> coinDtoList);

    List<CoinDto> getCoinList();
}

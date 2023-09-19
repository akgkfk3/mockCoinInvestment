package sesac.batchAndAlarm.repository;

import sesac.batchAndAlarm.domain.CoinDto;

import java.util.ArrayList;

public interface CoinRepository {

    void updateAllCoin(ArrayList<CoinDto> coinDtoList);
}

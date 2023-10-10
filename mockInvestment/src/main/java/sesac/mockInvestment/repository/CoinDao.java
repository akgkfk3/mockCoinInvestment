package sesac.mockInvestment.repository;

import sesac.mockInvestment.domain.CoinDto;

import java.util.List;

public interface CoinDao {

    List<CoinDto> getCoinList();
}

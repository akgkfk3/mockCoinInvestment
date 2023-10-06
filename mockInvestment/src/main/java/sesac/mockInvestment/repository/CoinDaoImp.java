package sesac.mockInvestment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sesac.mockInvestment.domain.CoinDto;
import sesac.mockInvestment.utils.JdbcUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CoinDaoImp implements CoinDao {

    private final DataSource dataSource;

    private final JdbcUtil jdbcutil;

    @Override
    public List<CoinDto> getCoinList() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CoinDto> coinList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM COINLIST";
            conn = dataSource.getConnection();

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                CoinDto coin = new CoinDto();
                coin.setMarket("KRW");
                coin.setName(rs.getString("CoinName"));
                coin.setOpening_price(rs.getDouble("Opening_Price"));
                coin.setLow_price(rs.getDouble("Low_Price"));
                coin.setHigh_price(rs.getDouble("High_Price"));
                coin.setTrade_price(rs.getDouble("Trade_Price"));
                coin.setPrev_closing_price(rs.getDouble("Prev_Closing_Price"));
                coin.setAcc_trade_price_24h(rs.getDouble("Acc_Trade_Price_24H"));

                coinList.add(coin);
            }

        } catch (SQLException e) {
            log.info("SQL Exception!!! {}", e.getMessage());
        } finally {
            jdbcutil.close(rs);
            jdbcutil.close(pstmt);
            jdbcutil.close(conn);
        }
        return coinList;
    }
}

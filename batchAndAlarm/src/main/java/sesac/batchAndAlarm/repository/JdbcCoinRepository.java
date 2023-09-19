package sesac.batchAndAlarm.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sesac.batchAndAlarm.domain.CoinDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

@Repository
@Slf4j
@RequiredArgsConstructor
public class JdbcCoinRepository implements CoinRepository {

    private final DataSource dataSource;

    /**
     * 파라미터로 CoinDto 타입의 List 파라미터를 받아 DB 테이블 (CoinList)에 Update 하는 메소드입니다.
     * @Author 박성수
     * @param ArrayList<CoinDto>
     * @Return 없음
     */
    @Override
    public void updateAllCoin(ArrayList<CoinDto> coinList) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        int updateCheck = 0;

        try {
            conn = dataSource.getConnection();

            Iterator<CoinDto> it = coinList.iterator();

            while (it.hasNext()) {
                CoinDto coin = it.next();

                // CoinName 컬럼은 Unique 제약 조건이 설정되어 인덱스가 자동 생성되기 때문에
                // 레코드 단위로 Lock이 설정됩니다.
                String sql = "update COINLIST set " +
                        "Opening_Price = ? ," +
                        "Low_Price = ? ," +
                        "High_Price = ? ," +
                        "Trade_Price = ? ," +
                        "Prev_Closing_Price = ? ," +
                        "Acc_Trade_Price_24H = ?" +
                        "WHERE CoinName = ?";
                pstmt = conn.prepareStatement(sql);

                pstmt.setDouble(1, coin.getOpening_price());
                pstmt.setDouble(2, coin.getLow_price());
                pstmt.setDouble(3, coin.getHigh_price());
                pstmt.setDouble(4, coin.getTrade_price());
                pstmt.setDouble(5, coin.getPrev_closing_price());
                pstmt.setDouble(6, coin.getAcc_trade_price_24h());
                pstmt.setString(7, coin.getName());

                updateCheck += pstmt.executeUpdate();
                pstmt.close();
            }

            // Update가 모두 성공하였을 시 commit 아니면 rollback 처리
            if (updateCheck == coinList.size()) {
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException ex) {
            log.info("SQL Exception Error!! {}", ex);
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.info("pstmt.close() Error!! {}", e);
            }
        }
    }
}

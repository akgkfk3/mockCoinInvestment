package sesac.mockInvestment.test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();

        // 현재 날짜/시간 출력
        System.out.println(now); // 2021-06-17T06:43:21.419878100

        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

        // 포맷팅 현재 날짜/시간 출력
        System.out.println(formatedNow);  // 2021년 06월 17일 06시 43분 21초
        System.out.println(formatedNow);  // 2021년 06월 17일 06시 43분 21초
        System.out.println(formatedNow);  // 2021년 06월 17일 06시 43분 21초
        System.out.println(formatedNow);  // 2021년 06월 17일 06시 43분 21초
        System.out.println(formatedNow);  // 2021년 06월 17일 06시 43분 21초



    }
}

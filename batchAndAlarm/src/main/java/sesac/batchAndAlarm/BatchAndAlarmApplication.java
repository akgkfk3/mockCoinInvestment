package sesac.batchAndAlarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BatchAndAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchAndAlarmApplication.class, args);
	}
}

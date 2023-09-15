package sesac.mockInvestment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sesac.mockInvestment.interceptor.LoginInterceptor;

import javax.sql.DataSource;

import static com.zaxxer.hikari.util.IsolationLevel.TRANSACTION_READ_COMMITTED;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource createDatasource() {
        // hikari Config 객체 생성
        HikariConfig hikariConfig = new HikariConfig();

        // DB 설정
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setPoolName("sesacMySQL");
        hikariConfig.setMaximumPoolSize(15);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setTransactionIsolation(String.valueOf(TRANSACTION_READ_COMMITTED));

        return new HikariDataSource(hikariConfig);
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css");
    }*/
}

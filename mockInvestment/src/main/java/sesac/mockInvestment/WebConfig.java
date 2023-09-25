package sesac.mockInvestment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sesac.mockInvestment.argumentresolver.LoginMemberArgumentResolver;
import sesac.mockInvestment.interceptor.LoginCheckInterceptor;
import sesac.mockInvestment.interceptor.LoginInterceptor;

import javax.sql.DataSource;

import java.net.Authenticator;
import java.util.List;

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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "css/**", "/*.ico", "assets/*", "img/*", "/error", "/member","/mypage", "/editMember", "/deleteMember");



//        registry.addInterceptor(new LoginInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/logout", "css/**", "/*.ico", "/error", "/mypage");


//        registry.addInterceptor(new LoginInterceptor())
//                .order(2)
//                .addPathPatterns("/**") // 모든 경로에 인터셉터 적용
//                .excludePathPatterns("/", "/logout", "css/**", "/*.ico", "/error", "/mypage");
    }



    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css");
    }*/
}

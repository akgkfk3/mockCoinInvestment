package sesac.mockInvestment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.minio.MinioClient;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sesac.mockInvestment.argumentresolver.LoginMemberArgumentResolver;
import javax.sql.DataSource;
import java.time.Duration;
import java.util.List;
import static com.zaxxer.hikari.util.IsolationLevel.TRANSACTION_READ_COMMITTED;

@Configuration
@NoArgsConstructor
@EnableRedisHttpSession
public class WebConfig implements WebMvcConfigurer {

    // MySQL 설정
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String mysqlPWD;


    // MiniO 설정
    @Value("${minio.endpoint.url}")
    private String minioUrl;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;
    
    // Redis 설정
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private String redisPWD;

    @Bean
    public DataSource createDatasource() {
        // hikari Config 객체 생성
        HikariConfig hikariConfig = new HikariConfig();

        // DB 설정
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(mysqlPWD);
        hikariConfig.setPoolName("sesacMySQL");
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setTransactionIsolation(String.valueOf(TRANSACTION_READ_COMMITTED));

        return new HikariDataSource(hikariConfig);
    }

    // 레디스 설정
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(redisPWD);
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate() {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
//
//        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
//
////        stringRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        stringRedisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        stringRedisTemplate.afterPropertiesSet();
//        return stringRedisTemplate;
//    }
//
//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
//        return new GenericJackson2JsonRedisSerializer();
//    }

    @Bean
    public MinioClient createMinioClient() {
        return MinioClient.builder().endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        redisConfig.setPassword(redisPWD);
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisOperations<String, Object> sessionRedisOperations() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedisSessionRepository sessionRepository(RedisOperations<String, Object> sessionRedisOperations) {
        RedisSessionRepository redisSessionRepository = new RedisSessionRepository(sessionRedisOperations);
        redisSessionRepository.setDefaultMaxInactiveInterval(Duration.ofSeconds(1800));
        return redisSessionRepository;
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
}

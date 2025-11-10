package web.mvc.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//환경설정을 돕는 전용 클래스(서버가 start될때 이게 붙은 클래스를 찾아서 @Bean을 실행, 리턴하는 객체를 bean으로 등록
//id = method 이름.
@Configuration
@Slf4j
@RequiredArgsConstructor
public class QueryDSLConfig {
    private final EntityManager em;

    @Bean
    public JPAQueryFactory getQueryFactory() {
        log.info("In QueryDSLConfig.JPAQueryFactory");
        log.info("entityManager : {}", this.em);
        return new JPAQueryFactory(em);
    }
}

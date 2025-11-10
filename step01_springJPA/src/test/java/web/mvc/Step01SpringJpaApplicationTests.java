package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 통합테스트(프로젝트 전체를 test)
@Slf4j
class Step01SpringJpaApplicationTests {

    @BeforeAll
    static void beforAll(){
        log.info("before all");
    }

    @AfterAll
    static void afterAll(){
        log.info("after all");
    }

    @BeforeEach
    void beforeEach(){
        log.info("before each");
    }

    @AfterEach
    void afterEach(){
        log.info("after each");
    }


    @Test
    @DisplayName("기본테스트")
    @Disabled // 테스트에서 제외.
    void contextLoads() {
        log.info("기본테스트");
    }
    
    @Test
    @DisplayName("기본테스트2")
    void contextLoads2() {
        log.info(("기본테스트2"));
    }
}

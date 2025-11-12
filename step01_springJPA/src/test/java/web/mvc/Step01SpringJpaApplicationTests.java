package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    @Test
    @DisplayName("api테스트")
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://snubh.recruiter.co.kr/app/jobnotice/list.json";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String response = restTemplate.postForObject(url, entity, String.class);
        System.out.println(response);
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> stringObjectMap = jsonParser.parseMap(response);
        stringObjectMap.forEach((k,v)->{
            System.out.println(k);
        });
    }

    @Test
    @DisplayName("get요청 테스트")
    public void get() {
        String kakaoUrl = "https://careers.kakao.com/jobs?skillSet=&page=1&company=KAKAO&part=TECHNOLOGY&employeeType=&keyword=";
        String naverUrl = "https://recruit.navercorp.com/rcrt/list.do?subJobCdArr=1010004#n";
        RestTemplate restTemplate = new RestTemplate();
        String url = naverUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
    }
}

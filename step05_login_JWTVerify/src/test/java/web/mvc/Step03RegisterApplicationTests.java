package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import web.mvc.domain.Member;
import web.mvc.repository.MemberRepository;

@SpringBootTest
@Slf4j
class Step03RegisterApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    String id = "test1358";
    @Test
    void contextLoads() {
        log.info("passwordEncoder : {}", passwordEncoder);

        //비문 -> 암호화
        String encoded = passwordEncoder.encode(id);
        log.info("encoded : {}", encoded);

        //암호화된 비번과 평문 비교!
        if(passwordEncoder.matches(id,encoded)){
            log.info("같음");
        }else {
            log.info("다름");
        }
    }

    @Test
    @DisplayName("관리자계정 추가")
    @Rollback(false)
    void memberInsert(){
        String encPwd =  passwordEncoder.encode("1234");
        memberRepository.save(Member.builder()
                        .id("admin")
                        .pwd(encPwd)
                        .role("ROLE_ADMIN")
                        .address("오리역")
                        .name("dh")
                .build());
    }

}

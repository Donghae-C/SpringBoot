package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Bank;
import web.mvc.repository.BankRepository;

@SpringBootTest// 통합테스트 + 자동커밋
@Slf4j
class Step03TransactionApplicationTests {
  @Autowired
  private BankRepository bankRepository;

    @Test
    @DisplayName("계좌등록")
    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)//단위테스트..?
    void bankInsert() {
        log.info("bankInsert called");
        bankRepository.save(Bank.builder().account("A01").balance(1000).build());
        bankRepository.save(Bank.builder().account("A02").balance(500).build());
        log.info("bankInsert done");
    }
    @Test
    @DisplayName("테스트")
    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)
    void test(){
        log.info("test called");
        Bank a01 = bankRepository.findById("A01").orElse(null);
        System.out.println(a01);
        a01.setBalance(1000);
    }
}

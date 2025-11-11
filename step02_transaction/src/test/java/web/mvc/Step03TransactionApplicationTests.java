package web.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.domain.Bank;
import web.mvc.repository.BankRepository;

@SpringBootTest// 통합테스트 + 자동커밋
class Step03TransactionApplicationTests {
  @Autowired
  private BankRepository bankRepository;

    @Test
    @DisplayName("계좌등록")
    void bankInsert() {
        bankRepository.save(Bank.builder().account("A01").balance(1000).build());
        bankRepository.save(Bank.builder().account("A02").balance(500).build());
    }
}

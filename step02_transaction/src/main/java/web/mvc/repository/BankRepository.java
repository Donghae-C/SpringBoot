package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Bank;

public interface BankRepository extends JpaRepository<Bank, String> {

}

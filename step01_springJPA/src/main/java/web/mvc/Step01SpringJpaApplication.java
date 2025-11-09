package web.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.mvc.repository.BoardRepository;

@SpringBootApplication
public class Step01SpringJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(Step01SpringJpaApplication.class, args);
    }

}

package web.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Step02LoginFormApplication {

    public static void main(String[] args) {
        SpringApplication.run(Step02LoginFormApplication.class, args);
    }

}

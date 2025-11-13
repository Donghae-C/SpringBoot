package web.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(){
        log.info("home called");
        return "Spring security start!";
    }

    /**
     * 로그인 폼
     */
    @GetMapping("/loginPage")
    public String loginPage(){
        log.info("loginPage called");
        return "loginform 화면임";
    }

    @GetMapping("/home")
    public String homeForm(@AuthenticationPrincipal UserDetails user){
        log.info("homeForm called");
        //인증된 경우에 실행된다..!
        System.out.println("user.getUsername():"+user.getUsername());
        System.out.println("user : " + user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user2 = (UserDetails) authentication.getPrincipal();
        System.out.println("user2.getUsername():"+user2.getUsername());
        System.out.println("user2 : " + user2);


        return "Authentication Successful";
    }
}

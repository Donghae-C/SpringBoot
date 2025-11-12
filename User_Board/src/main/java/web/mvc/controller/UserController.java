package web.mvc.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web.mvc.domain.User;
import web.mvc.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{url}")
    public void user(@PathVariable String url){

    }

    @PostMapping("/user/loginCheck")
    public String login(User user, HttpSession session){
        User logined = userService.loginCheck(user);
        log.info("logined : {}", logined);
        if(logined!=null){
            session.setAttribute("loginUser",logined);
        }
        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

package web.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String index(Model model){
        log.info("index called");
        model.addAttribute("message","index called");
        return "index"; //WEB-INF/views/index.jsp
    }
}

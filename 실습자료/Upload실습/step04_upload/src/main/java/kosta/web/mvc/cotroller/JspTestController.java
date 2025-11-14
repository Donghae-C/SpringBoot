package kosta.web.mvc.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
public class JspTestController {

    @RequestMapping("/")
    public String aa(){
        System.out.println("a요청됨..");

        return "index";
    }

}

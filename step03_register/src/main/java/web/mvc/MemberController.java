package web.mvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.Member;
import web.mvc.service.MemberService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/index")
    public ResponseEntity<?> index(){
        log.info("index");
        return ResponseEntity.status(HttpStatus.OK).body("Spring Security Setting 완료..");
    }

    @GetMapping("members/{id}")
    public ResponseEntity<?> duplicateCheck(@PathVariable String id){
        log.info("id : {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.duplicateCheck(id));
    }

    @PostMapping("/members")
    public ResponseEntity<?> signUp(@RequestBody Member member){
        memberService.signUp(member);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}

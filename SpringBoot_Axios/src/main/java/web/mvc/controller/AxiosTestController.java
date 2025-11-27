package web.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.UserReq;
import web.mvc.exception.MemberAuthenticationException;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
//@CrossOrigin("http://localhost:5173")
public class AxiosTestController {

    @GetMapping("/a")
    public String test(){
        log.info("여기 오니??");
        return "OK";
    }

    /**
     * 등록하기
     * */
    @PostMapping("/user")
    public String insert(@RequestBody  UserReq userReq){
        System.out.println("userReq = " + userReq);
        return "ok";
    }

    /**
     * 삭제하기
     * */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        System.out.println("id = " + id);

        if(id !=5){
            throw new MemberAuthenticationException("아아디값 유효하지 않아요.","ID Bad", HttpStatus.BAD_REQUEST);
        }

        return  ResponseEntity.status(HttpStatus.OK).body("OK");
    }



   /**
    * 수정하기
    * */
    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id , @RequestBody UserReq userReq){
        System.out.println("id = " + id);

        System.out.println("userReq = " + userReq);
        return ResponseEntity.status(HttpStatus.OK).body("1");
    }

   /**
    * 부분검색
    * */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> select(@PathVariable String id){
        System.out.println("id = " + id);
        UserReq userReq = new UserReq("Chan","chan@naver.com",50);

        return ResponseEntity.status(HttpStatus.OK).body(userReq);
    }


     /**
      * 전체검색
      * */
    @GetMapping("/users")
    public ResponseEntity<?> selectAll(){
        List<UserReq> list = new ArrayList<>();
        list.add(new UserReq("A","a@daum.net",10));
        list.add(new UserReq("B","b@daum.net",20));
        list.add(new UserReq("C","c@daum.net",30));

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}

package web.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.entity.Board;
import web.mvc.service.BoardService;

@RestController
@RequiredArgsConstructor
public class AjaxController {
    private final BoardService boardService;

    @GetMapping("/test")
    public String test(){
        return "Spring boot start";
    }

    @PostMapping("/boards")
    public ResponseEntity<?> saveBoards(@RequestBody Board board){
        boardService.save(board);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/boards/{bno}")
    public ResponseEntity<?> getBoard(@PathVariable long bno){
        return ResponseEntity.status(HttpStatus.OK).body(boardService.findById(bno));
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getBoards(){
        return ResponseEntity.status(HttpStatus.OK).body(boardService.findAll());
    }
}

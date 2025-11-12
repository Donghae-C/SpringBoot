package web.mvc.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.Reply;
import web.mvc.service.ReplyService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reply/writeForm")
    public String writeForm(Long bno, Model model){
        model.addAttribute("bno", bno);
        return "/reply/write";
    }

    @PostMapping("/reply/insert")
    public String insert(Reply reply, FreeBoard freeBoard){
        log.info("reply : {}", reply);
        log.info("freeBoard : {}", freeBoard);
        reply.setFreeBoard(freeBoard);
        replyService.insert(reply);
        return "redirect:/board/read/"+freeBoard.getBno()+"?flag=a";
    }

    @GetMapping("/reply/delete/{rno}/{bno}")
    public String delete(@PathVariable("rno") long rno, @PathVariable("bno") long bno){
        replyService.delete(rno);
        return "redirect:/board/read/"+bno;
    }
}

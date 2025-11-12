package web.mvc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.User;
import web.mvc.dto.FreeBoardDTO;
import web.mvc.service.FreeBoardService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    @Value("${BLOCK_COUNT}")
    private int BLOCK_COUNT;

    @GetMapping("/board/{url}")
    public void showFreeBoard(@PathVariable String url, Model model, @RequestParam(defaultValue = "1") int nowPage,
                              @RequestParam(defaultValue = "10") int pageSize) {
        int blockCount = BLOCK_COUNT;


        Pageable pageable = PageRequest.of(nowPage-1, pageSize, Sort.by("bno").descending());
        //List<FreeBoardDTO> freeList = freeBoardService.selectAll();
        Page<FreeBoardDTO> freeList = freeBoardService.selectAll(pageable);
        for(FreeBoardDTO FreeBoardDTO:freeList.getContent()){
            log.info("{}",FreeBoardDTO);
        }

        int currentPage = freeList.getNumber()+1;
        int totalPages = freeList.getTotalPages();

        int startPage = ((currentPage - 1)/blockCount) * blockCount + 1;

        model.addAttribute("nowPage",currentPage);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("startPage",startPage);
        model.addAttribute("blockCount",blockCount);
        model.addAttribute("pageList", freeList);
    }


    @PostMapping("/board/insert")
    public String insertFreeBoard(FreeBoard freeBoard) {
        freeBoardService.insert(freeBoard);
        return "redirect:/board/list";
    }

    @GetMapping("/board/read/{bno}")
    public String readFreeBoard(@PathVariable Long bno, String flag, Model model) {
        boolean state = flag == null;
        FreeBoard freeBoard = freeBoardService.selectBy(bno, state);
        model.addAttribute("board", freeBoard);
        return "/board/read";
    }

    @PostMapping("/board/updateForm")
    public String updateFreeBoardForm(FreeBoard freeBoard, Model model) {
        FreeBoard board = freeBoardService.selectBy(freeBoard.getBno(), false);
        model.addAttribute("board", board);
        return "/board/update";
    }

    @PostMapping("/board/update")
    public String updateFreeBoard(FreeBoard freeBoard) {
        freeBoardService.update(freeBoard);
        return "redirect:/board/read/"+freeBoard.getBno()+"?flag=a";
    }

    @PostMapping("/board/delete")
    public String deleteFreeBoard(Long bno, String password) {
        freeBoardService.delete(bno, password);
        return "redirect:/board/list";
    }

}

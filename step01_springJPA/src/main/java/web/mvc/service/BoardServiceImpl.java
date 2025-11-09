package web.mvc.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.entity.Board;
import web.mvc.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @PostConstruct
    public void init(){
        log.info("init BoardServiceImpl : {}", boardRepository);
        System.out.println("init BoardServiceImpl : " +  boardRepository );
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Board> findAll() {

        return boardRepository.findAll();
    }
}

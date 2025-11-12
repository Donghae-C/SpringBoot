package web.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.Reply;
import web.mvc.repository.ReplyRepository;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public void insert(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    public void delete(Long id) {
        replyRepository.deleteById(id);
    }
}

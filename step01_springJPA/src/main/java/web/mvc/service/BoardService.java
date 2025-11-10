package web.mvc.service;

import web.mvc.entity.Board;

import java.util.List;

public interface BoardService {
    /**
     * 등록하기
     */
    void save(Board board);
    /**
     * 조회하기
     */
    Board findById(Long id);
    /**
     * 전체조회하기
     */
    List<Board> findAll();

    void update(Board board);
}

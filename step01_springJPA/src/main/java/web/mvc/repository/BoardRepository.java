package web.mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import web.mvc.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board>{

    /**
     * 전달된 글번호보다 큰 레코드 검색
     * @param bnoAfter
     * @return
     */
    List<Board> findAllByBnoIsAfter(Long bnoAfter);

    List<Board> findByTitleLike(String title);

    List<Board> findByBnoLessThanAndTitleLike(Long bnoIsLessThan, String title);

    List<Board> findByTitleLikeAndContentLike(String title, String content);

    Page<Board> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

    /**
     * 글번호를 인수로 받아서 인수보다 큰 레코드 삭제
     */
    @Query("delete from Board b where b.bno > :bno")
    @Modifying
    void delGreateByBno(Long bno);;

    /**
     * 글번호 or 제목을 인수로 전달받아 검색함
     */
    @Query(value = "select b from Board b where b.bno >= :bno and b.title like :title")
    List<Board> findByBnoTitle(Long bno, String title);

    /**
     * 네이티브타입
     * @param bno
     * @param title
     * @return
     */
    @Query(value = "select * from board where bno >= :bno and title like :title",  nativeQuery = true)
    List<Board> findByBnoAndTitle(Long bno, String title);

    /**
     * 글번호, 제목, 작성자 인수로 전달.......... 검색
     */
    @Query(value = "select b from Board b where b.bno = :#{#bo.bno} or b.title = :#{#bo.title} or b.writer = :#{#bo.writer}")
    List<Board> findByWhere(@Param("bo") Board board);
}

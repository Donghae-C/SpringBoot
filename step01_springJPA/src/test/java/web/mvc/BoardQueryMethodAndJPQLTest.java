package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import web.mvc.entity.Board;
import web.mvc.repository.BoardRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@Slf4j
public class BoardQueryMethodAndJPQLTest {
    @Autowired
    private BoardRepository boardRepository;
    
    @Test
    @DisplayName("쿼리메서드 이용 검색")
    void findAllByBno(){
        List<Board> list = boardRepository.findAllByBnoIsAfter(100L);
        System.out.println(list);
    }

    @Test
    @DisplayName("쿼리메서드 이용, 제목검색")
    void findByTitle(){
        List<Board> list = boardRepository.findByTitleLike("%test1%");
        System.out.println(list);
    }

    @Test
    @DisplayName("글번호, 작성자 기준...")
    void findByBnoLessThanAndTitle(){
        List<Board> list = boardRepository.findByBnoLessThanAndTitleLike(150L, "%test1%");
        System.out.println(list);
    }

    @Test
    @DisplayName("작성자, 내용 검색")
    void findByTitleLikeAndContentLike(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> testPage = boardRepository.findByTitleLikeOrContentLike("%test11%", "%test11%", pageable);
        testPage.getContent().forEach(System.out::println);
    }

    @Test
    @DisplayName("입력한 글 번호보다 더 큰 번호들은 삭제")
    void deleteGratethan(){
        boardRepository.delGreateByBno(190L);
    }

    @Test
    @DisplayName("입력한 번호보다 크고, 제목검색")
    void fintByBnoAndTitle(){
        List<Board> list = boardRepository.findByBnoTitle(10L, "%test3%");
        for (Board board : list) {
            System.out.println("test + " + board);
        }
    }

    @Test
    @DisplayName("이건 네이티브 쿼리 스타일")
    void findByBnoAndTitleNative(){
        List<Board> list = boardRepository.findByBnoAndTitle(10L, "%test3%");
        for (Board board : list) {
            System.out.println("test + " + board);
        }
    }

    @Test
    @DisplayName("JPQL문법 3 - 여러조건")
    void findByWhere(){
        List<Board> list = boardRepository.findByWhere(Board.builder().title("test23").content("test50").bno(39L).build());
        for (Board board : list) {
            System.out.println("test + " + board);
        }
    }
}

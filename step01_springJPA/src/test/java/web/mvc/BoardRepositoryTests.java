package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import web.mvc.entity.Board;
import web.mvc.repository.BoardRepository;

import java.util.List;

//@SpringBootTest // 통합테스트. 이건 기본적으로 커밋이 됨. DataJpaTest는 롤백이 기본.
@DataJpaTest // jpa리포지토리.. 영속성관련 테스트. 기본적으로 내장DB(h2)를 사용해서 테스트 수행. 기본이 transaction.. 이건 롤백이 기본.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 기본이 h2니까 기존 db가 아니라 h2로 바꾸려고 하는데.. 그걸 막는거
@Rollback(false)//범위에 따라서 메서드위인지 아니면 클래스위인지 선택. 롤백하지 않고 commit하게 만듦
@Slf4j
// @RequiredArgsConstructor // 이거로 생성자 주입 불가능함
public class BoardRepositoryTests {
    //@Autowired
    //private BoardService boardService; @SpringBootTest에서 가능. DataJpaTest에선 @Repository 만 가능

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("초기화 test")
    void init(){
        log.info("boardRepository : {}", boardRepository);
    }

    @Test
    @DisplayName("등록하기")
    void insert(){
        //저장(등록)기능
        Board save = boardRepository.save(Board.builder()
                .title("test2")
                .writer("tester2")
                .content("test2")
                .build());
        log.info("save : {}", save);
    }

    @Test
    @DisplayName("수정하기")
    void update(){
        //save 메서드는 pk를 설정하지 않으면 insert기능. pk가 있으면 update가 됨.
        /*Board save = boardRepository.save(Board.builder()
                .bno(4L)
                .title("testupdated")
                .writer("testerudpated")
                .content("testupdated")
                .build());*/
        Board board = boardRepository.findById(3L).orElse(null);
        board.setTitle("testupdated2");
        board.setContent("testupdated2");
        board.setWriter("testupdated2");

        log.info("save : {}", board);
    }

    @Test
    @DisplayName("여러개 등록")
    @Disabled
    void insertMore(){
        for(int i = 6;i<=200;i++){
            boardRepository.save(Board.builder()
                    .title("test" + i)
                    .writer("tester" + i)
                    .content("testcontent" + i)
                    .build());
        }
        log.info("save성공");
    }

    @Test
    @DisplayName("전부보기")
    void selectAll(){
        List<Board> list = boardRepository.findAll();
        //출력
        //list.forEach((board)-> System.out.println(board));
        /*for(Board board : list){
            System.out.println(board);
        }*/
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("pk검색")
    void selectOne(){
        Board board = boardRepository.findById(20L).orElseThrow(()->new RuntimeException("음.."));

        log.info("pk : {}", board);
    }

    @Test
    @DisplayName("수정하기")
    void updateByPrimaryKey(){
        boardRepository.findById(25L).ifPresent(board -> {
            board.setTitle("testupdate");
            board.setContent("testupdate");
            board.setWriter("testupdate");
        });
        log.info("수정완료");
    }

    @Test
    @DisplayName("삭제")
    void deleteByPrimaryKey(){
        boardRepository.deleteById(10L);
    }//얘는 삭제가 되든 안되든.. void라서 뭔가가 안뜸

    /**
     * 페이징처리..!!
     */
    @Test
    @DisplayName("페이징처리")
    void page(){
        Pageable pageable = PageRequest.of(2, 15, Sort.by("title").descending());
        Page<Board> page = boardRepository.findAll(pageable);
        System.out.println("**************************");
        System.out.println("page.getTotalElements() : " + page.getTotalElements());
        System.out.println("page.getNumber() : " + page.getNumber());
        System.out.println("page.getSize() : " + page.getSize());
        System.out.println("page.getTotalPages() : " + page.getTotalPages());
        System.out.println("page.previousPageable() : " + page.previousPageable());
        System.out.println("page.nextPageable() : " + page.nextPageable());
        System.out.println("page.isFirst() : " + page.isFirst());
        System.out.println("page.isLast() : " + page.isLast());
        System.out.println("page.hasNext() : " + page.hasNext());
        System.out.println("page.hasPrevious() : " + page.hasPrevious());
        System.out.println("page.getNumberOfElements() : " + page.getNumberOfElements());
        page.getContent().forEach(System.out::println);

    }
}

package web.mvc;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import web.mvc.entity.Board;
import web.mvc.entity.QBoard;

import java.time.LocalDateTime;
import java.util.List;

//@DataJpaTest //Repository가 아니라서 Factory 주입이 안됨
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BoardQueryDSLTests {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    @DisplayName("초기화 test")
    public void test(){
        log.info("jpaQueryFactory : {}", jpaQueryFactory);
    }


    /**
     * 검색
     */
    @Test
    @DisplayName("검색")
    public void test2(){
        QBoard board = QBoard.board;
        List<Board> list = jpaQueryFactory.selectFrom(board).fetch();
        list.forEach(System.out::println);
    }

    /**
     * 검색(조건)
     */
    @Test
    @DisplayName("검색(조건)")
    public void test3(){
        QBoard board = QBoard.board;
        List<Board> list = jpaQueryFactory
                .selectFrom(board)
                .where(board.bno.lt(30L).or(board.bno.eq(100L)).or(board.bno.gt(150L)))
                .fetch();
        list.forEach(System.out::println);
    }

    /**
     * 삭제
     */
    @Test
    @DisplayName("삭제")
    @Transactional
    @Rollback(false)//기본이 rollback이라 취소해줘야함............
    public void test4(){ //transaction 설정 안해주면 에러남...
        QBoard board = QBoard.board;
        long execute = jpaQueryFactory.  //삭제된 레코드 수의 값이 나옴
                delete(board).
                where(board.bno.eq(100L)).
                execute();
        log.info("execute : {}", execute);
    }

    /**
     * 수정
     */
    @Test
    @DisplayName("수정")
    @Transactional
    @Rollback(false)
    public void test5(){
        QBoard board = QBoard.board;
        jpaQueryFactory
                .update(board)
                .set(board.writer, "updated2")
                .set(board.title, "updated2")
                .set(board.content, "updated2")
                .set(board.updateTime, LocalDateTime.now())
                .where(board.bno.eq(58L))
                .execute();
    }
}

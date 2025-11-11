package web.mvc;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import web.mvc.dto.BoardDTO;
import web.mvc.entity.Board;
import web.mvc.entity.QBoard;
import web.mvc.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@DataJpaTest //Repository가 아니라서 Factory 주입이 안됨
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BoardQueryDSLTests {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("초기화 test")
    public void test(){
        log.info("jpaQueryFactory : {}", jpaQueryFactory);
        log.info("boardRepository : {}", boardRepository);
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

    /// /QuerydslPredicateExecutor<>사용하기 //////////////////////
    /**
     * interface에 QuerydslPredicateExecutor<> 상속받는다.
     *   - QuerydslPredicateExecutor안에서 제공하는 메소드를 사용해서 자바중심으로 조건(Predicate)을 만들수 있다.
     *   -Spring Data JPA + QueryDSL을 접목한 형태로 Repository에서 바로 QueryDSL의
     *   `Predicate`를 실행할 수 있도록 지원한다.
     *   - JPAQueryFactory 없이 간단하게 Predicate로 해결
     *   ex)  ~.findAll(Predicate p)
     *
     *   참고 : https://www.notion.so/QuerydslPredicateExecutor-T-208a7a6c42ce80b290bde247b33a49ef?pvs=12
     * */
    @Test
    public void queryDSL05(){
        QBoard board = QBoard.board;
        //BooleanBuilder는 Querydsl에서 동적 쿼리를 만들 때 조건(where절)을 유연하게 조립하기 위한 도우미 객체
        BooleanBuilder builder = new BooleanBuilder();

        //1)
        builder.and(board.bno.eq(9L)); //b1_0.bno=?
        builder.or(board.title.like("%User1%"));

        //2) insert_date between ? and ?
        LocalDateTime from = LocalDateTime.of(2025, 11, 10,0,0,0);
        LocalDateTime to = LocalDateTime.of( 2025, 11, 11,12,0,0);
        builder.and(board.createTime.between(from, to)); //insert_date between ? and ?

        //3)
        //builder.and(board.writer.eq("user1")); //대소문자구분한다.

        //4)
        //builder.and(board.author.equalsIgnoreCase("user9"));  //대소문자 구분안한다.

        //5)
        //builder.and(board.author.toUpperCase().eq("user9".toUpperCase()));

        //6)
        //builder.and(board.author.toUpperCase().eq("user9".toUpperCase())).or(board.bno.gt(140L));


        Iterable<Board> it = boardRepository.findAll(builder);

        //Iterable 를 List형태로 변환 하고 싶다.
        List<Board> list = Lists.newArrayList(it);

        list.forEach(System.out::println);
    }
/// /////QuerydslPredicateExecutor 와 JPAQuryFactory 비교 //////////////////
    /**
     * 제목에 키워드가 포함되고, 작성자가 특정인인 게시글 조회 - BooleanBuilder이용
     * */
    @Test
    public void queryDSL06(){
        String titleKeyword = "제목10";
        String authorName = null;
        QBoard board = QBoard.board;

        //BooleanBuilder는 여러 조건을 동적으로 조합할 때 매우 유용
        BooleanBuilder builder = new BooleanBuilder();

        if (titleKeyword != null && !titleKeyword.isEmpty()) {
            builder.and(board.title.containsIgnoreCase(titleKeyword));
        }

        if (authorName != null && !authorName.isEmpty()) {
            builder.and(board.writer.eq(authorName));
        }

        Iterable<Board> it = boardRepository.findAll(builder);
    }

    /**
     * 제목에 키워드가 포함되고, 작성자가 특정인인 게시글 조회 -
     *  jpaQueryFactory의 where절에 직접 조건식 사용
     * */
    @Test
    public void queryDSL07(){
        String titleKeyword = "제목10";
        String authorName = null;

        QBoard board = QBoard.board;

        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .where(
                        titleKeyword != null && !titleKeyword.isEmpty() ? board.title.containsIgnoreCase(titleKeyword) : null,
                        authorName != null && !authorName.isEmpty() ? board.writer.eq(authorName) : null
                )
                .fetch();
    }
    /**
     * 제목에 키워드가 포함되고, 작성자가 특정인인 게시글 조회 - List<BooleanExpression>
     * */
    @Test
    public void queryDSL08(){
        String titleKeyword = "제목10";
        String authorName = null;

        QBoard board = QBoard.board;

        // 동적 조건 조립 (null 체크 후 where절에 넣기 위해)
        List<BooleanExpression> conditions = new ArrayList<>();

        if (titleKeyword != null && !titleKeyword.isEmpty()) {
            conditions.add(board.title.containsIgnoreCase(titleKeyword));
        }

        if (authorName != null && !authorName.isEmpty()) {
            conditions.add(board.writer.eq(authorName));
        }

// 조건 배열을 가변 인자로 넘김
        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .where(conditions.toArray(new BooleanExpression[0]))
                .fetch();
    }


    /////////////////////////////////////////////////////////////////////
    /**
     * QueryDSL의 Projections
     *  : QueryDSL의 Projections는 쿼리 결과를 DTO에 매핑할 때 사용하는 도구
     :참고 - https://www.notion.so/QueryDSL-Projections-208a7a6c42ce803cb746d4bfce586ee1
     * */
    @Test
    public void translateDTO(){
        QBoard board = QBoard.board;
        List<BoardDTO>  list =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        BoardDTO.class,
                                        board.bno,
                                        board.title,
                                        board.writer,
                                        board.content,
                                        board.createTime,
                                        board.updateTime)
                        )
                        .from(board)
                        .fetch();

        list.forEach(b->System.out.println(b));
    }

}


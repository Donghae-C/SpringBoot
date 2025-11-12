package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import web.mvc.domain.FreeBoard;
import web.mvc.repository.FreeBoardRepository;

@SpringBootTest
@Slf4j
public class FreeBoardJoinFetchTests {

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    /**
     * JPQL join fetch에서 페이징 처리 해보자
     * */
    @Test
    @DisplayName("join fetch, Page리턴 사용")
    void join04() {
        Pageable pageable =
                PageRequest.of(0,5 , Sort.Direction.DESC , "bno");
        //countQuery적용
        Page<FreeBoard> page = freeBoardRepository.join03(pageable);

        System.out.println("***************************");
        System.out.println("page.getNumber() = "+page.getNumber());
        System.out.println("page.getSize() = "+page.getSize());
        System.out.println("page.getTotalPages() = "+page.getTotalPages());
        System.out.println("page.previousPageable() = "+page.previousPageable());
        System.out.println("page.nextPageable() = "+page.nextPageable());

        System.out.println("page.isFirst() = "+page.isFirst());
        System.out.println("page.isLast() = "+page.isLast());

        System.out.println("page.hasPrevious() = "+page.hasPrevious());
        System.out.println("page.hasNext() = "+page.hasNext());
        System.out.println("*****************************************");

        System.out.println("list.size = " + page.getContent().size());
        //list.forEach(b->System.out.println(b.getBno() +" = " + b.getReplyList().size()));

        page.getContent().forEach(b->{
            System.out.println(b.getBno()+" | " + b.getSubject());
            b.getRepliesList().forEach(r->{
                System.out.println("====> " +r.getRno()+" | " +r.getContent()+" | "+ r.getRno());
            });
            System.out.println();
        });
    }


    /**
     * JPQL문법을 이용하여
     * join fetch + 페이징처리 쿼리
     *     @Query(value = "select distinct f from FreeBoard f  left join fetch f.repliesList",
     *     countQuery = "select count(distinct f.bno) from FreeBoard f left join f.repliesList" )
     *     Page<FreeBoard> join05(Pageable page);
     * */
    @Test
    @DisplayName("join fetch + countQuery")
    void join05() {
        Pageable pageable =
                PageRequest.of(0,5 , Sort.Direction.DESC , "bno");
        //countQuery적용
        Page<FreeBoard> page = freeBoardRepository.join05(pageable);

        System.out.println("***************************");
        System.out.println("page.getNumber() = "+page.getNumber());
        System.out.println("page.getSize() = "+page.getSize());
        System.out.println("page.getTotalPages() = "+page.getTotalPages());
        System.out.println("page.previousPageable() = "+page.previousPageable());
        System.out.println("page.nextPageable() = "+page.nextPageable());

        System.out.println("page.isFirst() = "+page.isFirst());
        System.out.println("page.isLast() = "+page.isLast());

        System.out.println("page.hasPrevious() = "+page.hasPrevious());
        System.out.println("page.hasNext() = "+page.hasNext());
        System.out.println("*****************************************");

        System.out.println("list.size = " + page.getContent().size());
        //list.forEach(b->System.out.println(b.getBno() +" = " + b.getReplyList().size()));

        page.getContent().forEach(b->{
            System.out.println(b.getBno()+" | " + b.getSubject());
            b.getRepliesList().forEach(r->{
                System.out.println("====> " +r.getRno()+" | " +r.getContent()+" | "+ r.getRno());
            });
            System.out.println();
        });
    }

}

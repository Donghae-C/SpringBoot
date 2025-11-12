package web.mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import web.mvc.domain.FreeBoard;

import java.util.List;
import java.util.Optional;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long>, QuerydslPredicateExecutor<FreeBoard> {
    @Query(value = "select f from FreeBoard f left join fetch f.repliesList")
    List<FreeBoard> join01();

    //EntityGraph//
    @EntityGraph(attributePaths = "repliesList", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select f from FreeBoard f")
    List<FreeBoard> join02();

    @EntityGraph(attributePaths = "repliesList", type = EntityGraph.EntityGraphType.LOAD) //이거도 되고
    @Query("select f from FreeBoard f")
    Page<FreeBoard> join03(Pageable pageable);

    @Query("select f from FreeBoard f left join fetch f.repliesList where f.bno=:bno")
    Optional<FreeBoard> join04(@Param("bno") Long bno);

    @Query(value = "select f from FreeBoard f  left join fetch f.repliesList",
            countQuery = "select count(distinct f.bno) from FreeBoard f left join  f.repliesList" )
    Page<FreeBoard> join05(Pageable page);
}

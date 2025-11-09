package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long> {

}

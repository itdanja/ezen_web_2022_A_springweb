package ezenweb.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // DAO 역할
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

}

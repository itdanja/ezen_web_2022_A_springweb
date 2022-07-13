package ezenweb.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<MessageEntity , Integer> {
    @Query(value = "select count(*) from message where tomno = :mno and isread = 0 " , nativeQuery = true)
    Integer getisread( @Param("mno") int mno );
}

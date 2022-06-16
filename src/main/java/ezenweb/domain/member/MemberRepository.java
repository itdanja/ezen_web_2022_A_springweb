package ezenweb.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity , Integer> {

}

//   JpaRepository      [ CRUD ]
    // 1. findAll() : 모든 엔티티 호출
    // 3. findbyId( pk값 ) : 해당 pk의 엔티티 호출
    // 3. save( 엔티티 ) : 해당 엔티티를 DB 레코드 추가
    // 4. delete( 엔티티 ) : 해당 엔티티를 삭제 처리
    // ?????????? 수정은 없다 ~~~ [ 매핑된 엔티티는 JPA 자동감지 지원 ]
            // 엔티티를 수정하면 자동으로 DB 수정된다.



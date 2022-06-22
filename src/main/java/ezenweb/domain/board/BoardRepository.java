package ezenweb.domain.board;

import ezenweb.domain.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  // DAO 역할
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    // 1. 검색 메소드
        // 1. .findAll() : 모든 엔티티 검색
        // 2. .findbyId( pk값 ) : 해당 pk의 엔티티 검색
        // 3. [직접 선언]  :   .findby필드명( 값 ) : 해당 필드명에서 값에 해당하는  하나 엔티티 검색        [ Optional ]
        // 4. [직접 선언] :    .findAllBy필드명( 값 ) : 해당 필드명에서 값에 해당하는 여러개 엔티티 검색   [ List<엔티티명> ]

        // 1. 제목 검색
        List<BoardEntity> findAllBybtitle(String keyword  );
        // 2. 내용 검색
        List<BoardEntity> findAllBybcontent(String keyword  );
        // 3. 작성자 검색
//        List<BoardEntity> findAllBymno( String keyword  );

}

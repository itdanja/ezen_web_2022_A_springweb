package ezenweb.domain.board;

import ezenweb.domain.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  // DAO 역할
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    // 1. 검색 메소드
        // 1. .findAll() : 모든 엔티티 검색
        // 2. .findbyId( pk값 ) : 해당 pk의 엔티티 검색
        // 3. [직접 선언]  :   .findby필드명( 값 ) : 해당 필드명에서 값에 해당하는  하나 엔티티 검색        [ Optional ]
        // 4. [직접 선언] :    .findBy필드명( 값 ) : 해당 필드명에서 값에 해당하는 여러개 엔티티 검색   [ List<엔티티명> ]
        // 5. [ 직접 쿼리작성 ] :  @Query( value = "쿼리문작성" , nativeQuery = true )
                        // SQL에 변수 넣기
                             // * 필드(column) 명은 변수 으로 불가능
                            // * @Param ( ) 생략가능
                           //  *     :변수명      ,    ?인수순서번호
                            // 1.   [인수] @Param("변수명") String 변수명   ->   [SQL ]  :변수명
                            // 2.   [ 인수] @Param("변수명") 엔티티 변수명   --> [ SQL ]   :#{ #엔티티명.필드명 }

        // 1. 제목 검색
            // 1. sql 없이
                // List<BoardEntity> findBybtitle(  String keyword  );
            // 2. sql 적용
//            @Query( value = "select * from board where btitle = :keyword" , nativeQuery = true )
//            List<BoardEntity> findBybtitle( @Param("keyword") String keyword  );
        @Query( value = "select * from board where btitle like %:keyword%" , nativeQuery = true )
        List<BoardEntity> findBybtitle(  @Param("keyword")  String keyword  );
        // 2. 내용 검색
        @Query( value = "select * from board where bcontent like %:keyword%" , nativeQuery = true )
        List<BoardEntity> findBybcontent(   @Param("keyword") String keyword  );
        // 3. 작성자 검색
        @Query( value = "select * from board where mno = :#{#memberEntity.mno}", nativeQuery = true  )
        List<BoardEntity> findBymno(    @Param("memberEntity") MemberEntity memberEntity  );

}

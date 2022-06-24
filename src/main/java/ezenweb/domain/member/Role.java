package ezenweb.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor// 생성자
@Getter
public enum Role {      // p.178
    // 자바 자료형
        // 1. 클래스 Class
        // 2. 인터페이스
        // 3. 열거형 enum [ 서로 연관된 필드들의 집합 구성 ]

    // 열거형!!! =  MEMBER[0] , INTERME[1] , ADMIN[2]
    MEMBER("ROLE_MEMBER" , "회원") ,
    INTERME("ROLE_INTERME" , "중개인"),
    ADMIN("ROLE_ADMIN" , "관리자");

    // 열거형 들어가는 필드 항목들
    private final String key ;   // final[상수] : 데이터 고정
    private final String title;
}

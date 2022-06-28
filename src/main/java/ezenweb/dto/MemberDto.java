package ezenweb.dto;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.Role;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class MemberDto {

    // 필드
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
    private String memail;
    // DTO -> ENTITY
    public MemberEntity toentitiy(){
        // 패스워드 암호화
            //  BCrypt : 레인보우 테이블 공격 방지를 위해 솔트(Salt)를 통합한 적응형 함수 [ 32비트 ]
            //  랜덤의 Salt 부여하여 여러번 해시를 적응 ---> 암호 해독 어렵다...
            //  숨길데이터 + 랜덤데이터  -> 다른 진수로 변환
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return MemberEntity.builder() // 빌더패턴 : 포함하지 않는 필드는 0 또는 null 자동 대입
                .mid( this.mid)
                .mpassword(   passwordEncoder.encode(  this.mpassword ) )
                .mname(this.mname)
                .memail( this.memail)
                .roomEntityList( new ArrayList<>() )
                .role( Role.MEMBER ) // 권한부여
                .build();
    }
}




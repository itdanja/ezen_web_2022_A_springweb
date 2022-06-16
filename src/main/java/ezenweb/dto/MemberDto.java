package ezenweb.dto;

import ezenweb.domain.member.MemberEntity;
import lombok.*;

import java.util.ArrayList;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class MemberDto {

    // 필드
    private int mno;
    private String mid;
    private String mpasswrd;
    private String mname;

    // DTO -> ENTITY
    public MemberEntity toentitiy(){
        return MemberEntity.builder() // 빌더패턴 : 포함하지 않는 필드는 0 또는 null 자동 대입
                .mid( this.mid)
                .mpasswrd(this.mpasswrd)
                .mname(this.mname)
                .roomEntityList( new ArrayList<>() )
                .build();
    }

}




package ezenweb.domain.member;

import ezenweb.domain.BaseTime;
import ezenweb.domain.board.BoardEntity;
import ezenweb.domain.message.MessageEntity;
import ezenweb.domain.room.RoomEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
    private String memail;
    private String oauth;   // 일반회원=인증  /oauth 구분용

    // DB에 저장될 enum 타입 설정
    // @Enumerated( EnumType.ORDINAL ) // 열거형 인덱스 번호
    @Enumerated( EnumType.STRING ) // 열거형 이름
    private Role role;
    // 권한[role] 중에서 key 값 반환 메소드 선언
    public String getrolekey() {  // 시큐리티에서 인증허가 된 리스트에 보관하기 위해서
        return role.getKey();
    }

    @OneToMany( mappedBy = "memberEntity" , cascade = CascadeType.ALL)
    List<RoomEntity> roomEntityList ;

    @Builder.Default    // 빌더 사용시 초기값 설정
    @OneToMany( mappedBy ="memberEntity" , cascade = CascadeType.ALL)  // 1:M
    List<BoardEntity> boardEntityList = new ArrayList<>();

    // 보낸 메시지 리스트
    @Builder.Default    // 빌더 사용시 초기값 설정
    @OneToMany( mappedBy ="fromentity" , cascade = CascadeType.ALL)  // 1:M
    List<MessageEntity> fromentitylist = new ArrayList<>();
    // 받은 메시지 리스트
    @Builder.Default    // 빌더 사용시 초기값 설정
    @OneToMany( mappedBy ="toentity" , cascade = CascadeType.ALL)  // 1:M
     List<MessageEntity> toentitylist = new ArrayList<>();
}


// extends : 상속 [ 슈퍼클래스로부터 메모리 할당 ]
















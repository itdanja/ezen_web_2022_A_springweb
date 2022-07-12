package ezenweb.domain.message;

import ezenweb.domain.BaseTime;
import ezenweb.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder@Table( name = "message")  // 테이블 이름 정의
public class MessageEntity extends BaseTime {
    // 1. 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgno;
    // 2. 작성내용
    private String msg;
    // 3. 읽음여부
    private boolean isread;
    // 4. 보낸사람
    @ManyToOne
    @JoinColumn(name="frommno")
    private MemberEntity fromentity;
    // 5. 받는사람
    @ManyToOne
    @JoinColumn(name="tomno")
    private MemberEntity toentity;
}

package ezenweb.domain.board;

import ezenweb.domain.BaseTime;
import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.Role;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Type;

@Entity // 테이블과 매핑
@Getter @Setter
@ToString( exclude = { "memberEntity" , "categoryEntity" } ) // [ 객체주소값 대신 데이터로 출력해주는 메소드 ]
@AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name="board")
public class BoardEntity extends BaseTime {
    @Id // pk
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto
    private int bno;                // 번호
    private String btitle;         // 제목
    private String bcontent;    // 내용
    private int bview;              // 조회수
    private int blike;              // 좋아요 수

    // 작성자 [ 연관관계 ]
    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;
    // 카테고리 [ 연관관계 ]
    @ManyToOne
    @JoinColumn( name = "cno")
    private CategoryEntity categoryEntity;
    // 첨부파일 [ 연관관계 ]
    // 댓글 [ 연관관계 ]
}

package ezenweb.domain.board;

import ezenweb.domain.BaseTime;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 테이블과 매핑
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardEntity extends BaseTime {
    @Id // pk
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto
    private int bno;                // 번호
    private String btitle;         // 제목
    private String bcontent;    // 내용
    private int bview;              // 조회수
    private int blike;              // 좋아요 수
    // 작성자 [ 연관관계 ]
    // 첨부파일 [ 연관관계 ]
    // 카테고리 [ 연관관계 ]
    // 댓글 [ 연관관계 ]
}
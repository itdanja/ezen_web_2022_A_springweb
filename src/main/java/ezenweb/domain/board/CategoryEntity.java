package ezenweb.domain.board;

import ezenweb.domain.BaseTime;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // 테이블과 매핑
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name="category")
public class CategoryEntity extends BaseTime {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int cno;
    private String cname;
    // board 연관관계
    @Builder.Default
    @OneToMany(mappedBy = "categoryEntity" , cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntityList = new ArrayList<>();
}

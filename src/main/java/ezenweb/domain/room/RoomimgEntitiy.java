package ezenweb.domain.room;

import ezenweb.domain.BaseTime;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Roomimg")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString( exclude="roomEntity" )@Builder
public class RoomimgEntitiy extends BaseTime {

    // pk번호
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rimgno;
    // 이미지이름
    private String rimg;
    // 방 번호 [ FK ]
    @ManyToOne
    @JoinColumn( name = "rno")
    private RoomEntity roomEntity;






}

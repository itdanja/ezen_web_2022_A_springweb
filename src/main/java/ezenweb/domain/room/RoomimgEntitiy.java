package ezenweb.domain.room;

import lombok.*;

import javax.persistence.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor@Builder
@Entity @Table( name="roomimg")
public class RoomimgEntitiy {

    // pk번호
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rimgno;
    // 이미지이름
    private String rimg;
    // 방 번호 [ FK ]

}

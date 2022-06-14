package ezenweb.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter@ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table( name = "room")  // 테이블 이름 정의
public class RoomEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rno;
    private String roomname;
    private String x;
    private String y;
    private String rimg;

}

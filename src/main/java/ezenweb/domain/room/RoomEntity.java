package ezenweb.domain.room;

import ezenweb.domain.BaseTime;
import ezenweb.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter@ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table( name = "room")  // 테이블 이름 정의
public class RoomEntity extends BaseTime { // Entity = 개체

    @Id // PK
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // AUTO KEY
    private int rno;                            // 방 번호  = PK , AUTO KEY
    private String rtitle;                      // 방 타이틀
    private String rlat;                                 // 위도
    private String rlon;                                 // 경도
    private String rtrans;                              // 거래방식[전세/월세/매매]
    private String rprice;//                           가격
    private String rarea;//                            면적
    private String rmanagementfee;//          관리비
    private String rstructure;//                     구조
    private String rcompletiondate;//         준공날짜
    private String rindate;//                     입주가능일
    private String rkind;//                           건물종류
    private String raddress;//                     주소
    private String ractive;                                // 거래 상태
    private int rfloor;//                                        현재 층
    private int rmaxfloor;//                                        건물 전체 층
    private boolean rparking;//                    주차 여부
    private boolean relevator;//                    엘리베이터 여부
    @Column( columnDefinition = "TEXT")
    private String rcontents;//                        상세설명

    @OneToMany( mappedBy = "roomEntity" , cascade = CascadeType.ALL )
    private List<RoomimgEntitiy > roomimgEntitiyList ;

    @ManyToOne
    @JoinColumn( name = "mno" )
    private MemberEntity memberEntity;


}

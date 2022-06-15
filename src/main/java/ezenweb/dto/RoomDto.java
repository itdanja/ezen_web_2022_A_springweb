package ezenweb.dto;

import ezenweb.domain.room.RoomEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RoomDto {

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
    private String rcontents;//                                                상세설명

    private List<MultipartFile> rimg;

    //  Dto -> entitiy 메소드
            // 1. 생성자
            // 2. 빌더 패턴 [ 빌더에 포함되지않는 필드는 0 또는 null ]
            // 3. ModelMapper 라이브러리
    public RoomEntity toentity() {
        return  RoomEntity.builder()
                .rno( this.rno )
                .rtitle( this.rtitle )
                .rlat( this.rlat)
                .rlon( this.rlon)
                .rtrans( this.rtrans)
                .rprice( this.rprice)
                .rarea( this.rarea)
                .rmanagementfee( this.rmanagementfee)
                .rstructure(this.rstructure)
                .rcompletiondate( this.rcompletiondate)
                .rindate( this.rindate)
                .rkind( this.rkind)
                .raddress( this.raddress)
                .ractive( this.ractive)
                .rfloor( this.rfloor)
                .rmaxfloor( this.rmaxfloor)
                .rparking( this.rparking)
                .relevator( this.relevator)
                .rcontents( this.rcontents )
                .roomimgEntitiyList( new ArrayList<>( ) )
                .build();
    }


}

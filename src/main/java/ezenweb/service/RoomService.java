package ezenweb.service;

import ezenweb.domain.RoomEntity;
import ezenweb.domain.RoomRepository;
import ezenweb.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // 1. 룸 저장
    public boolean room_save(RoomDto roomDto) {
        // dto -> entitiy
        RoomEntity roomEntity = RoomEntity.builder()
                .roomname(roomDto.getRoomname())
                .x(roomDto.getX())
                .y(roomDto.getY())
                .build();
        // 저장
        roomRepository.save(roomEntity);
        return true;
    }

    // 2. 룸 호출
        // 반환타입   {   키 : [ { }, {} , {} ,{}  ]  }
        //          JSON vs 컬렉션프레임워크
        //          JSONObject == MAP
        //          JSONArray  == List
                                //  { 키 : 값 } = entry      --> Map 컬렉션
                                //  [  요소1 , 요소2 , 요소3 ] --> List 컬렉션
                                //  List< Map<String,String> >
                                // { "positions" : [  ]  }
                                // Map<String , List< Map<String,String> > >
    // 1.  JSON
    /*
    public JSONObject room_list() {
        JSONArray jsonArray = new JSONArray();
        // 1. 모든 엔티티 호출
        List<RoomEntity> roomEntityList = roomRepository.findAll(); // 엔티티에 생성자 없으면 오류발생
        // 2. 모든 엔티티 -> json 변환
        for (RoomEntity roomEntity : roomEntityList) {

            JSONObject object = new JSONObject();

            object.put("rname", roomEntity.getRoomname());
            object.put("lng", roomEntity.getX());
            object.put("lat", roomEntity.getY());

            jsonArray.put(object);
        }

        JSONObject object = new JSONObject();
        object.put("positions" , jsonArray );

        // 3. 반환
        return object;
    }
     */

    // 2.
    public // 접근제한자
        Map< String , List<  Map<String , String >  > >     // 반환타입
        room_list   // 메소드명
        ( Map<String,String> Location ) // 인수
     {

        List<  Map<String , String >  > Maplist = new ArrayList<>();

        // 현재 보고 있는 지도 범위
             double qa = Double.parseDouble(   Location.get("qa")    );
             double pa = Double.parseDouble(   Location.get("pa")    );
             double ha = Double.parseDouble(   Location.get("ha")    );
             double oa = Double.parseDouble(   Location.get("oa")    );

        // 1.모든 엔티티 꺼내오기 ~~~~
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        // 2. 엔티티 -> map -> 리스트 add
        for( RoomEntity entity : roomEntityList  ){ // 리스트에서 엔티티 하나씩 꺼내오기

            // [ 조건 ]   Location 범위내 좌표만 저장 하기
            if(  Double.parseDouble(  entity.getY() ) > qa
                    && Double.parseDouble(  entity.getY() ) < pa
                    && Double.parseDouble(  entity.getX() )   > ha
                    && Double.parseDouble(  entity.getX() )   < oa
            ) {
                // 3. map 객체 생성
                Map<String, String> map = new HashMap<>();
                map.put("rname", entity.getRoomname());
                map.put("lng", entity.getX());
                map.put("lat", entity.getY());
                // 4. 리스트 넣기
                Maplist.add(map);
            }
        }
        Map< String , List<  Map<String , String >  > > object = new HashMap<>();
        object.put( "positions" , Maplist );

        return  object;

    }
}

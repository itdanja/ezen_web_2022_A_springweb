package ezenweb.service;

import ezenweb.domain.room.RoomEntity;
import ezenweb.domain.room.RoomRepository;
import ezenweb.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // 1. 룸 저장
    public boolean room_save(RoomDto roomDto) {

        // dto -> entitiy
       RoomEntity roomEntity = roomDto.toentity();
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.map( roomDto , RoomEntity.class);


        String uuidfile = null;
        // 첨부파일
        if( roomDto.getRimg().size() != 0 ){    // 첨부파일이 1개 이상이면

            //1. 반복문를 이용한 모든 첨부파일 호출
            for(MultipartFile file : roomDto.getRimg() ){

                // 파일명이 동일하면 식별 문제 발생 ~~~~~~~~~~~
                    // 1. UUID 난수 생성
                UUID uuid = UUID.randomUUID();
                    // 2. UUID+ 파일명  [  // .getOriginalFilename() :  실제 첨부파일 이름 ]
                uuidfile = uuid.toString() +"_"+ file.getOriginalFilename().replaceAll("_","-");
                    // UUID 와 파일명 구분 _ 사용 [ 만약에 파일명에 _존재하면 문제발생 -> 파일명 _  ----->   -  변경  ]

                // 2. 경로 설정
                //        \:제어문자
                String dir  = "C:\\Users\\505-t\\Desktop\\spring\\springweb\\src\\main\\resources\\static\\upload\\";
                String filepath = dir+uuidfile;

                try {
                    // 3. **** 첨부파일 업로드 처리
                    file.transferTo( new File(filepath) );


                    // 첨부파일.transferTo( 새로운 경로->파일 ) ;
                }catch( Exception e ){ System.out.println("파일저장실패 : "+ e);}
            }

        }

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
            if(  Double.parseDouble(  entity.getRlat() ) > qa
                    && Double.parseDouble(  entity.getRlat() ) < pa
                    && Double.parseDouble(  entity.getRlon() )   > ha
                    && Double.parseDouble(  entity.getRlon() )   < oa
            ) {
                // 3. map 객체 생성
                Map<String, String> map = new HashMap<>();
                map.put("rno", entity.getRno()+"" );
                map.put("rtitle", entity.getRtitle());
                map.put("lon", entity.getRlon());
                map.put("lat", entity.getRlat());
                // 4. 리스트 넣기
                Maplist.add(map);
            }
        }
        Map< String , List<  Map<String , String >  > > object = new HashMap<>();
        object.put( "positions" , Maplist );

        return  object;

    }
}

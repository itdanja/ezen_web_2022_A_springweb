package ezenweb.service;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.domain.room.RoomEntity;
import ezenweb.domain.room.RoomRepository;
import ezenweb.domain.room.RoomimgEntitiy;
import ezenweb.domain.room.RoomimgRepository;
import ezenweb.dto.LoginDto;
import ezenweb.dto.RoomDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomimgRepository roomimgRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    // 1. 룸 저장
    @Transactional  // 트랜잭션
    public boolean room_save(RoomDto roomDto) {

        String mid = memberService.getloginmid();

        // 현재 로그인된 회원의 엔티티 찾기
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findBymid( mid );
        MemberEntity memberEntity =  null;
        if( optionalMemberEntity.isPresent() ){ memberEntity = optionalMemberEntity.get(); }
        else{ return false; }

        // 1.  dto -> entitiy   [ dto는 DB에 저장할수 없으니까~  ]
        RoomEntity roomEntity = roomDto.toentity();             // 1. 객체 생성
                                                                                            // 자동으로 DTO -> entity 변환 라이브러리
                                                                                    //        ModelMapper modelMapper = new ModelMapper();
                                                                                    //        modelMapper.map( roomDto , RoomEntity.class);
        // 2. 저장 [ 우선적으로 룸 DB에 저장한다. pk생성 ]
        roomRepository.save( roomEntity );                    //  2. 해당 객체가 매핑

            //  ** 현재 로그인된 회원 엔티티를 룸 엔티티에 저장
            roomEntity.setMemberEntity( memberEntity );
            //  ** 현재 로그인된 회원 엔티티내 룸 리스트에 룸 엔티티 추가
            memberEntity.getRoomEntityList().add( roomEntity );

        // 3. 입력받은 첨부파일를 저장한다.
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
                    // 1. 프로젝트내 이미지 저장 [ 프로젝트내 저장할경우 프로젝트를 다시 빌드 -> 서버 ]
//                String dir  = "C:\\Users\\505-t\\Desktop\\springweb\\ezen_web_2022_A_springweb\\src\\main\\resources\\static\\upload\\";
                    // 2. 서버에 이미지 저장 [ 다시 빌드X = 스프링부트는 내장서버이기때문에 = 서버 재시작시 초기화 ]
                String dir = "C:\\Users\\505-t\\Desktop\\ezen_web_2022_A_springweb\\build\\resources\\main\\static\\upload\\";

                String filepath = dir+uuidfile;

                try {
                    // 3. **** 첨부파일 업로드 처리
                    file.transferTo( new File(filepath) );

                    // 1. 룸 이미지 엔티티 객체  생성
                    RoomimgEntitiy roomimgEntitiy = RoomimgEntitiy.builder()
                            .rimg( uuidfile )
                            .roomEntity(  roomEntity  )
                            .build();

                    // 2. 룸 엔티티 세이브
                    roomimgRepository.save( roomimgEntitiy );

                    // 3. 이미지엔티티를 룸엔티티에 추가
                    roomEntity.getRoomimgEntitiyList().add( roomimgEntitiy );

                   // 첨부파일.transferTo( 새로운 경로->파일 ) ;
                }catch( Exception e ){ System.out.println("파일저장실패 : "+ e);}
            }

        }

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
                map.put("rlon", entity.getRlon());
                map.put("rlat", entity.getRlat());
                map.put("rimg", entity.getRoomimgEntitiyList().get(0).getRimg() );

                // 4. 리스트 넣기
                Maplist.add(map);
            }
        }
        Map< String , List<  Map<String , String >  > > object = new HashMap<>();
        object.put( "positions" , Maplist );

        return  object;

    }

    public JSONObject getroom( int rno ){

        // 1. 해당 룸번호의 룸엔티티 찾기
          Optional<RoomEntity> optionalRoomEntity =  roomRepository.findById( rno );
          RoomEntity roomEntity =  optionalRoomEntity.get();
         // 2.  해당 엔티티 -> json 객체 변환
          JSONObject object = new JSONObject();
                // 1. json에 엔티티 필드 값 넣기
          object.put("rtitle" , roomEntity.getRtitle() );
          object.put("mid" , roomEntity.getMemberEntity().getMid() );

          JSONArray jsonArray = new JSONArray();
                // 2. 룸엔티티의 저장된 룸이미지를 반복문을 이용한 룸이미지를 jsonarray에 저장
          for(  RoomimgEntitiy roomimgEntitiy : roomEntity.getRoomimgEntitiyList()  ) { //  룸별로 이미지 여러개
              jsonArray.put( roomimgEntitiy.getRimg() );
          }
            // 3. jsonarray를 json객체 포함
          object.put("rimglist" , jsonArray) ;
         // 3. 반한
          return object;
    }

    // 현재 로그인된 회원이 등록한 방 목록 호출
    public JSONArray myroomlist(){
        JSONArray jsonArray = new JSONArray();
        // 현재 로그인된 회원 엔티티  찾기
        LoginDto loginDto =  (LoginDto) request.getSession().getAttribute("login");
        MemberEntity memberEntity =  memberRepository.findById(  loginDto.getMno() ).get() ;
        // 찾은 회원 엔티티의 방 목록 json형으로 변환
        for( RoomEntity entity : memberEntity.getRoomEntityList() ) {
            JSONObject object = new JSONObject();
            object.put("rno" , entity.getRno() );
            object.put("rtitle" , entity.getRtitle() );
            object.put("rimg" , entity.getRoomimgEntitiyList().get(0).getRimg() );
            object.put("rdate" , entity.getModifiedate() );
            jsonArray.put( object  );
        }
        return jsonArray;
    }

    // 룸 삭제 메소드
    @Transactional
    public boolean delete( int rno ){
        // rno 해당 하는 엔티티 찾기
        RoomEntity roomEntity =  roomRepository.findById( rno ).get();

        if( roomEntity != null ){
            // 해당 엔티티를 삭제
            roomRepository.delete( roomEntity );
            return true;
        }else{
            return false;
        }
    }



}

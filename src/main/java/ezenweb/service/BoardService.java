package ezenweb.service;


import ezenweb.domain.board.BoardEntity;
import ezenweb.domain.board.BoardRepository;
import ezenweb.domain.board.CategoryEntity;
import ezenweb.domain.board.CategoryRepository;
import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.BoardDto;
import ezenweb.dto.LoginDto;
import ezenweb.dto.MemberDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardService {
    // jsp : DAO 호출   //  JPA : Repository 호출
    @Autowired  // 자동 빈 생성 [ 자동 생성자 이용한 객체에 메모리 할당 ]
    private BoardRepository boardRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // 1. C [ 인수 : 게시물 dto ]
    @Transactional
    public boolean save(BoardDto boardDto){

        // 1. 세션 호출 [ 시큐리티 사용시 -> 세션x -> 인증세션 ( UserDetails vs DefaultOAuth2User ) ]
//        LoginDto loginDto
//                = (LoginDto)request.getSession().getAttribute("login");
        // 1. 인증된 세션 호출 [ 시큐리티내 인증 결과 호출 ]
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 2. 인증 정보 가져오기
        Object principal = authentication.getPrincipal(); // Principal : 인증 정보
        // 3. 일반회원 : UserDetails   oauth회원 : DefaultOAuth2User   구분
            // java문법 :  자식객체 instanceof 부모클래스명 : 상속여부 확인 키워드
        String mid = null;
        if( principal instanceof UserDetails ){ // 인증정보의 타입이 UserDetails 이면 [ 일반회원 검증 ]
            mid = ((UserDetails) principal).getUsername(); // 인증정보에서 mid 호출
            System.out.println("일반 회원으로 글쓰기~~~~  " + principal.toString() );
        }else if( principal instanceof DefaultOAuth2User ){ // 인증정보의 타입이 DefaultOAuth2User 이면 [ oauth2회원 검증 ]
            System.out.println("oauth2 회원으로 글쓰기~~~~  " + principal.toString() );
            Map<String , Object>  map =  ((DefaultOAuth2User) principal).getAttributes();
            // 회원정보 요청키를 이용한 구분 짓기
            if( map.get("response") != null ){   // 1. 네이버 일경우  [ Attributes 에 response 이라는 키가 존재하면 ]
               Map< String , Object> map2  = (Map<String, Object>) map.get("response");
               mid = map2.get("email").toString().split("@")[0]; // 아이디만 추출
            }else{   // 2. 카카오 일경우
                Map< String , Object> map2  = (Map<String, Object>) map.get("kakao_account");
                mid = map2.get("email").toString().split("@")[0]; // 아이디만 추출
            }
        }else{ // 인증정보가 없을경우
            return false;
        }

        if( mid != null  ){ // 로그인 되어 있으면
            // 2. 로그인된 회원의 엔티티 찾기
            Optional<MemberEntity> optionalMember  = memberRepository.findBymid( mid );
                        // findById( pk키 ) => 반환타입 : Optional클래스 [ NULL 저장 ]
            if ( optionalMember.isPresent() ){ // null 아니면
                    // Optional클래스내 메소드 : .isPresent()    : null 이 아니면
                    // 3. Dto -> entity
                    // 만약에 기존에 있는 카테고리가 있으면
                    boolean sw = false;
                    int cno =  0 ;
                    List<CategoryEntity> categoryEntityList =  categoryRepository.findAll();
                    for( CategoryEntity entity : categoryEntityList){
                        if( entity.getCname().equals(  boardDto.getCategory())){
                            sw = true;
                            cno = entity.getCno();
                        }
                    }
                    CategoryEntity categoryEntity = null;
                    if( !sw ){ // 카테고리가 없을경우
                            // 1. 카테고리 생성
                            categoryEntity = CategoryEntity.builder()
                                    .cname( boardDto.getCategory())
                                    .build();
                            categoryRepository.save( categoryEntity );
                    }else{ // 카테고리 있을경우
                        categoryEntity = categoryRepository.findById( cno ).get();
                    }

                BoardEntity boardEntity =  boardRepository.save( boardDto.toentity()  );
                    // 4. 작성자 추가
                boardEntity.setMemberEntity( optionalMember.get() );
                boardEntity.setCategoryEntity( categoryEntity );

                        // 카테고리 엔티티 에 게시물 연결
                        categoryEntity.getBoardEntityList().add(  boardEntity );
                        // 회원엔티티에 게시물 연결
                        optionalMember.get().getBoardEntityList().add( boardEntity );
                    // 5.반환
                return true;
            }
        }else{ // 로그인이 안되어 있는경우
            return false;
        }
        return false;
    }
    // 2. R[ 인수 : x  반환: 1. JSON  2. MAP ]
    public JSONObject getboardlist( int cno ,String key , String keyword , int page  ){

        JSONObject object = new JSONObject();

        Page<BoardEntity> boardEntities = null ; // 선언만

        // Pageable : 페이지처리 관련 인테페이스
       // PageRequest : 페이징처리 관련 클래스
                    // PageRequest.of(  page , size ) : 페이징처리  설정
                            // page = "현재페이지"   [ 0부터 시작 ]
                            // size = "현재페이지에 보여줄 게시물수"
                            // sort = "정렬기준"  [   Sort.by( Sort.Direction.DESC , "정렬필드명" )   ]
                                // sort 문제점 : 정렬 필드명에 _ 인식 불가능 ~~~  ---> SQL 처리
        Pageable pageable = PageRequest.of( page , 3 , Sort.by( Sort.Direction.DESC , "bno")    ); // SQL : limit 와 동일 한 기능처리

        // 필드에 따른 검색 기능
        if(  key.equals("btitle") ){
            boardEntities = boardRepository.findBybtitle( cno ,  keyword , pageable );
        }else if( key.equals("bcontent") ){
            boardEntities = boardRepository.findBybcontent(  cno , keyword ,  pageable );
        }else if( key.equals("mid") ){
            // 입력받은 mid -> [ mno ] 엔티티 변환
                // 만약에 없는 아이디를 검색했으면
            Optional<MemberEntity> optionalMember=  memberRepository.findBymid( keyword );
            if( optionalMember.isPresent()){ // .isPresent() : Optional 이 null 아니면
                MemberEntity memberEntity = optionalMember.get(); // 엔티티 추출
                boardEntities = boardRepository.findBymno( cno ,  memberEntity , pageable  ); // 찾은 회원 엔티티를 -> 인수로 전달
            }else{ // null 이면
                return object; // 검색 결과가 없으면
            }
        }else{ // 검색이 없으면
            boardEntities = boardRepository.findBybtitle( cno , keyword ,  pageable );
        }

        // 페이지에 표시할 총 페이징 버튼 개수
        int btncount = 5;
        // 시작번호버튼 의 번호      [   ( 현재페이지 / 표시할버튼수 ) * 표시할버튼수 +1
        int startbtn  = ( page / btncount ) * btncount + 1;
        // 끝 번호버튼의 번호       [  시작버튼 + 표시할버튼수-1 ]
        int endhtn = startbtn + btncount -1;
            // 만약에 끝번호가 마지막페이지보다 크면 끝번호는 마지막페이지 번호로 사용
            if( endhtn > boardEntities.getTotalPages() ) endhtn = boardEntities.getTotalPages();

        // 엔티티 반환타입을 List 대신 Page 인터페이스 할경우에
//        System.out.println( "검색된 총 게시물 수 : "  + boardEntities.getTotalElements() );
//           System.out.println( "검색된 총 페이지 수 : " + boardEntities.getTotalPages() );
//        System.out.println( "검색된 게시물 정보 : " + boardEntities.getContent() );
//        System.out.println( "현재 페이지수 : " + boardEntities.getNumber() );
//        System.out.println( "현재 페이지의 게시물수 : " + boardEntities.getNumberOfElements() );
//        System.out.println( "현재 페이지가 첫페이지 여부 확인  : " +  boardEntities.isFirst() );
//        System.out.println( "현재 페이지가 마지막 페이지 여부 확인  : " +  boardEntities.isLast() );

        //*  data : 모든 엔티티 -> JSON 변환
        JSONArray jsonArray = new JSONArray();
        for( BoardEntity entity : boardEntities ){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bno", entity.getBno());
                jsonObject.put("btitle", entity.getBtitle());
                jsonObject.put("bindate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
                jsonObject.put("bview", entity.getBview());
                jsonObject.put("blike", entity.getBlike());
                jsonObject.put("mid", entity.getMemberEntity().getMid());
                jsonArray.put(jsonObject);
        }

        // js 보낼 jsonobect 구성
        object.put( "startbtn" , startbtn );       //  시작 버튼
        object.put( "endhtn" , endhtn );         // 끝 버튼
        object.put( "totalpages" , boardEntities.getTotalPages() );  // 전체 페이지 수
        object.put( "data" , jsonArray );  // 리스트를 추가

        return object;
    }
    // 2. R : 개별조회 [ 게시물번호 ]
    @Transactional
    public JSONObject getboard( int bno ){

        // 조회수 증가처리 [ 기준 : ip / 24시간 ]
        String ip = request.getRemoteAddr();    // 사용자의 ip 가져오기

        Optional<BoardEntity> optional =  boardRepository.findById( bno );
        BoardEntity entity = optional.get();

            // 세션 호출
            Object com =  request.getSession().getAttribute(ip+bno);
            if( com == null  ){ // 만약에 세션이 없으면
                // ip 와 bno 합쳐서 세션(서버내 저장소) 부여
                request.getSession().setAttribute(ip+bno , 1 );
                request.getSession().setMaxInactiveInterval( 60*60*24  ); // 세션 허용시간 [ 초단위  ]
                // 조회수 증가처리
                entity.setBview( entity.getBview()+1 );
            }

        JSONObject object = new JSONObject();
        object.put("bno" , entity.getBno() );
        object.put("btitle" , entity.getBtitle() );
        object.put("bcontent" , entity.getBcontent() );
        object.put("bview" , entity.getBview() );
        object.put("blike" , entity.getBlike() );
        object.put("bindate" , entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) );
        object.put("bmodate" , entity.getModifiedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) );
        object.put("mid" , entity.getMemberEntity().getMid()  );
        return object;
    }

    // 3. U [ 인수 : 게시물번호 , 수정할 내용들 -> dto ]
    @Transactional
    public boolean update( BoardDto boardDto){
        Optional<BoardEntity> optionalBoard
                =  boardRepository.findById( boardDto.getBno() );
        BoardEntity boardEntity =  optionalBoard.get();
        boardEntity.setBtitle( boardDto.getBtitle() );
        boardEntity.setBcontent( boardDto.getBcontent() );
        return true;
    }
    // 4. D [ 인수 : 게시물번호
    @Transactional
    public boolean delete( int bno ){
        BoardEntity boardEntity = boardRepository.findById( bno ).get();
        boardRepository.delete(  boardEntity );
        return true;
    }
    // 5. 카테고리 호출 메소드
    public JSONArray getcategorylist(){

        List<CategoryEntity> categoryEntityList
                = categoryRepository.findAll();

        JSONArray jsonArray = new JSONArray();
        for( CategoryEntity entity : categoryEntityList ){
            JSONObject object = new JSONObject();
            object.put("cno" , entity.getCno() );
            object.put("cname" , entity.getCname());
            jsonArray.put( object );
        }
        return jsonArray;
    }

}

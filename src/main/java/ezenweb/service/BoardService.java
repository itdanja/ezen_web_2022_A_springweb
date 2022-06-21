package ezenweb.service;


import ezenweb.domain.board.BoardEntity;
import ezenweb.domain.board.BoardRepository;
import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.BoardDto;
import ezenweb.dto.LoginDto;
import ezenweb.dto.MemberDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    // 1. C [ 인수 : 게시물 dto ]
    @Transactional
    public boolean save(BoardDto boardDto){

        // 1. 세션 호출
        LoginDto loginDto
                = (LoginDto)request.getSession().getAttribute("login");

        if( loginDto != null  ){ // 로그인 되어 있으면
            // 2. 로그인된 회원의 엔티티 찾기
            Optional<MemberEntity> optionalMember
                    = memberRepository.findById( loginDto.getMno() );
                        // findById( pk키 ) => 반환타입 : Optional클래스 [ NULL 저장 ]
            if ( optionalMember.isPresent() ){ // null 아니면
                    // Optional클래스내 메소드 : .isPresent()    : null 이 아니면
                    // 3. Dto -> entity
                BoardEntity boardEntity =  boardRepository.save( boardDto.toentity()  );
                    // 4. 작성자 추가
                boardEntity.setMemberEntity( optionalMember.get() );
                    // 5.반환
                return true;
            }
        }else{ // 로그인이 안되어 있는경우
            return false;
        }
        return false;
    }
    // 2. R[ 인수 : x  반환: 1. JSON  2. MAP ]
    public JSONArray getboardlist(){
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntities =  boardRepository.findAll();
        //* 모든 엔티티 -> JSON 변환
        for( BoardEntity entity : boardEntities ){
            JSONObject object = new JSONObject();
            object.put("bno" , entity.getBno() );
            object.put("btitle" , entity.getBtitle() );
            object.put("bindate" , entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) );
            object.put("bview" , entity.getBview() );
            object.put("blike" , entity.getBlike() );
            object.put("mid" , entity.getMemberEntity().getMid()  );
            jsonArray.put( object);
        }
        return jsonArray;
    }
    // 2. R : 개별조회 [ 게시물번호 ]
    public JSONObject getboard( int bno ){
        Optional<BoardEntity> optional =  boardRepository.findById( bno );
        BoardEntity entity = optional.get();
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

}

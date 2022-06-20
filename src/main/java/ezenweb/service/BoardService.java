package ezenweb.service;


import ezenweb.domain.board.BoardEntity;
import ezenweb.domain.board.BoardRepository;
import ezenweb.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    // jsp : DAO 호출   //  JPA : Repository 호출
    @Autowired  // 자동 빈 생성 [ 자동 생성자 이용한 객체에 메모리 할당 ]
    private BoardRepository boardRepository;

    // 1. C [ 인수 : 게시물 dto ]
    @Transactional
    public boolean save(BoardDto boardDto){
        // 1. Dto -> entity
        int bno =  boardRepository.save( boardDto.toentity()  ) .getBno();
        if( bno >= 1 ){ return true;}
        else{ return false ; }
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
            object.put("bdate" , entity.getCreatedate() );
            object.put("bview" , entity.getBview() );
            object.put("blike" , entity.getBlike() );
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
        object.put("bindate" , entity.getCreatedate() );
        object.put("bmodate" , entity.getModifiedate() );
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

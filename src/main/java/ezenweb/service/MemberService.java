package ezenweb.service;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    // 서비스구역 : 1.로직 / 2.트랜잭션


    @Autowired // 자동 빈(메모리) 생성      // @Autowired vs new
    private MemberRepository memberRepository;

    // 다른 클래스의 메소드나 필드 호출 방법!!!
    // * 메모리 할당 [ 객체 만들기 ]
    // 1. static : java 실행시 우선 할당 -> java 종료시 메모리 초기화
    // 2. 객체생성
    // 1. 클래스명 객체명 = new 클래스명()

    // 2. 객체명.set필드명 = 데이터

    // 3.   @Autowired
    //      클래스명 객체명;

    // 1. 로그인처리 메소드
    public boolean login( String mid , String mpassword ){

        // 1. 모든 엔티티 호출  [ java처리 조건처리 ]
        List<MemberEntity> memberEntityList =   memberRepository.findAll();
        // 2. 모든 엔티티 리스트에서 입력받은 데이터와 비교한다.
        for( MemberEntity entity : memberEntityList  ){
            // 3. 아이디와 비밀번호가 동일하며
            if( entity.getMid().equals(mid) && entity.getMpasswrd().equals(mpassword) ){
                return true; // 4. 로그인 성공
            }
        }
        return false; // 5. 로그인 실패
    }


    // 2. 회원가입처리 메소드
    public boolean signup(  MemberDto memberDto){
        // dto -> entitiy [ 이유 : dto는 DB로 들어갈수 없다~~ ]
        MemberEntity memberEntity = memberDto.toentitiy();
        // entitiy 저장
        memberRepository.save( memberEntity );
        // 저장여부 판단
        if( memberEntity.getMno() < 1 ){
            return false;
        }else{
            return true;
        }
    }


}
















package ezenweb.service;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    // 로직 / 트랜잭션
    // 1. 로그인처리 메소드
    public boolean login( ){
        return false;
    }

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


    // 2. 회원가입처리 메소드
    public boolean signup(  MemberDto memberDto){

        // dto -> entitiy [ 이유 : dto는 DB로 들어갈수 없다~~ ]
        MemberEntity memberEntity =
                MemberEntity.builder()
                        .mid( memberDto.getMid() )
                        .mpasswrd(memberDto.getMpasswrd())
                        .mname( memberDto.getMname())
                        .build();
        // entitiy 저장
        memberRepository.save( memberEntity );
        return false;
    }


}

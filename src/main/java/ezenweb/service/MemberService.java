package ezenweb.service;

import ezenweb.domain.MemberEntity;
import ezenweb.domain.MemberRepository;
import ezenweb.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Service
public class MemberService {

    // 로직 / 트랜잭션
    // 1. 로그인처리 메소드
    public boolean login( ){
        return false;
    }

    @Autowired
    private MemberRepository memberRepository;

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

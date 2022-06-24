package ezenweb.service;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.LoginDto;
import ezenweb.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

                                            // UserDetailsService 인터페이스 [ 추상메소드 존재~~ ]

    // * 로그인 서비스 제공 메소드
    // 1. 패스워드 검증 X [ 시큐리티 제공 ]
    // 2. 아이디만 검증 처리
    // 3. 권한 키 검증 처리
    @Override //
    public UserDetails loadUserByUsername(String mid ) throws UsernameNotFoundException {
        // 1. 회원 아이디로 엔티티 찾기
        Optional<MemberEntity> entityOptional =  memberRepository.findBymid( mid );
        MemberEntity memberEntity = entityOptional.orElse(null);
                                            // Optional 클래스 [ null 관련 오류 방지 ]
                                            // 1. optional.isPresent()   : null 아니면
                                            // 2. optional.orElse() : 만약에 optional객체가 비어있으면 반환할 데이터
        // 2. 찾은 회원엔티티의 권한[키] 을 리스트에 담기
        List<GrantedAuthority> authorityList = new ArrayList<>();
                //GrantedAuthority : 부여된 인증의 클래스
                //   List<GrantedAuthority> : 부여된 인증들을 모아두기

        authorityList.add(    new SimpleGrantedAuthority( memberEntity.getrolekey() ) );
                // 리스트에 인증된 엔티티의 키를 보관
        // 세션부여????????????????????   -> UserDetails  -> 인증되면 세션 부여
        return new LoginDto(  memberEntity , authorityList ); // 회원엔티티 , 인증된 리스트를  인증세션 부여
    }


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

    // 세션 호출
    @Autowired
    HttpServletRequest request; // 세션 사용을 위한 request 객체 선언

    // 1. 로그인처리 메소드 [ 시큐리티 사용 하기 전 ]
//    public boolean login( String mid , String mpassword ){
//        // 1. 모든 엔티티 호출  [ java 조건처리 ]
//        List<MemberEntity> memberEntityList =   memberRepository.findAll();
//        // 2. 모든 엔티티 리스트에서 입력받은 데이터와 비교한다.
//        for( MemberEntity entity : memberEntityList  ){
//            // 3. 아이디와 비밀번호가 동일하며
//            if( entity.getMid().equals(mid) && entity.getMpasswrd().equals(mpassword) ){
//
//                // 로그인세션에 사용될 dto 생성
//                LoginDto logindto = LoginDto.builder()
//                        .mno(entity.getMno() )
//                        .mid( entity.getMid() )
//                        .mname( entity.getMname() )
//                        .build();
//
//                // 세션 객체 호출
//                request.getSession().setAttribute("login" , logindto ); // 세션이름 ,데이터
//
//                return true; // 4. 로그인 성공
//            }
//        }
//        return false; // 5. 로그인 실패
//    }

//    // 3. 로그아웃 메소드
//    public void logout(){
//        request.getSession().setAttribute("login",null); // 해당 세션을 null 대입
//    }
//


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

    // 4. 회원수정 메소드
    @Transactional
    public boolean update( String mname ){

        // 세션내 dto 호출
        LoginDto loginDto = (LoginDto) request.getSession().getAttribute("login");
        if( loginDto == null ){ // 세션이 없으면
            return false;
        }

        MemberEntity memberEntity =  memberRepository.findById( loginDto.getMno() ).get();
        memberEntity.setMname( mname );
        // 해당 엔티티의 필드를 수정하면 자동으로 DB도 수정된다.
        return true;
    }

    // 5. 회원탈퇴 메소드
    public boolean delete( String mpassword ){
        // 1. 세션 호출
        LoginDto loginDto = (LoginDto) request.getSession().getAttribute("login");
        // 2. 엔티티 호출
        MemberEntity memberEntity =  memberRepository.findById( loginDto.getMno() ).get();
        // 3. 삭제 처리 조건
        if( memberEntity.getMpassword().equals( mpassword) ){ // 만약에 해당 로그인된 패스워드와 입력받은 패스워드가 동일하면
            memberRepository.delete( memberEntity ); // 엔티티 삭제
            return true;
        }
        return false;
    }


}
















package ezenweb.service;

import ezenweb.domain.member.MemberEntity;
import ezenweb.domain.member.MemberRepository;
import ezenweb.dto.LoginDto;
import ezenweb.dto.MemberDto;
import ezenweb.dto.OauthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.mail.javamail.JavaMailSender;    // 자바 메일 전송 인터페이스

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User> {
                                            // UserDetailsService 인터페이스 [ 추상메소드 존재~~ ] : 일반 회원
                                                    // -----> loadUserByUsername 메소드 구현
                                            // OAuth2UserService<OAuth2UserRequest , OAuth2User> : Oauth2 회원
                                                    // ------> loadUser 메소드 구현

    //  *  oauth2 서비스 제공 메소드
    // OAuth2UserRequest : 인증 결과를 호출 클래스
    @Override
    public OAuth2User loadUser( OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 인증[로그인] 결과 정보 요청
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser( userRequest );

        // 클라이언트 아이디 [ 네이버 vs 카카오 vs 구글 ] : oauth 구분용 으로 사용할 변수
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 회원정보 요청시 사용되는 JSON 키 이름 호출  : 회원정보 호출시 사용되는 키 이름
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // 확인
        System.out.println(  "클라이언트(개발자)가 등록 이름 :   " + registrationId   );
        System.out.println(  "회원 정보(JSON) 호출시 사용되는 키 이름 :   " + userNameAttributeName   );
        System.out.println(  "회원 인증(로그인) 결과 내용  : " + oAuth2User.getAttributes() );

        // oauth2 정보 -> Dto -> entitiy -> db저장
        OauthDto oauthDto = OauthDto.of(  registrationId ,  userNameAttributeName  ,  oAuth2User.getAttributes()  );

        System.out.println( "oauthDto 확인 : " + oauthDto.toString() );

        //  1. 이메일로 엔티티호출
        Optional<MemberEntity> optional
                =  memberRepository.findBymemail( oauthDto.getMemail() );
        // 2. 만약에 엔티티가 없으면
        if( !optional.isPresent() ){
            memberRepository.save( oauthDto.toentity() );  // entity 저장
        }

        // 반환타입 DefaultOAuth2User ( 권한(role)명 , 회원인증정보 , 회원정보 호출키 )
            // DefaultOAuth2User , UserDetails : 반환시 인증세션 자동 부여 [ SimpleGrantedAuthority : (권한) 필수~  ]
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")),
                oAuth2User.getAttributes() ,
                userNameAttributeName
                );
    }

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

    @Autowired
    private JavaMailSender javaMailSender;      // 1.자바 메일 전송 인터페이스
    // 메일전송 메소드
    public void mailsend( String toemail , String title ,  StringBuilder content    ){ // 인수 : 받는사람이메일 , 제목 , 내용
        // SMTP : 간이 메일 전송 프로토콜 [ 텍스트 외 불가능 ]
        try {   // 이메일 전송
            MimeMessage message = javaMailSender.createMimeMessage();   //     Mime 프로토콜 :  메시지안에 텍스트외 내용을 담는 프로토콜 [  SMTP 와 같이 많이 사용됨]
            // 0. Mime 설정
            MimeMessageHelper mimeMessageHelper
                    = new MimeMessageHelper( message , true, "utf-8"); // 예외처리 발생
            // 1. 보내는사람
            mimeMessageHelper.setFrom("kgs2072@naver.com" , "Ezen 부동산");
            // 2. 받는 사람
            mimeMessageHelper.setTo( toemail );
            // 3. 메일 제목
            mimeMessageHelper.setSubject( title );
            // 4. 메일 내용
            mimeMessageHelper.setText( content.toString() , true);
            // 5. 메일 전송
            javaMailSender.send(  message );

        }catch( Exception e  ){
            System.out.println("메일전송 실패 : "+ e );
        }
    }

    // 2. 회원가입처리 메소드
    @Transactional
    public boolean signup(  MemberDto memberDto){
        // dto -> entitiy [ 이유 : dto는 DB로 들어갈수 없다~~ ]
        MemberEntity memberEntity = memberDto.toentitiy();
        // entitiy 저장
        memberRepository.save( memberEntity );

        // 저장여부 판단
        if( memberEntity.getMno() < 1 ){
            return false; // 회원가입 실패
        }else{
            // 이메일에 들어가는 내용 [ html ]
            StringBuilder html = new StringBuilder();   // StringBuilder : 문자열 연결 클래스 [ append 연결메소드 vs +:연결연산자  ]
            html.append("<html> <body> <h1> EZEN부동산 회원 이메일 검증 <h1> ");
                // 인증코드[ 문자 난수] 만들기
                Random random = new Random(); // 랜덤 객체
                    StringBuilder authkey = new StringBuilder();
                for( int i = 0 ; i<12 ; i++ ){ // 12자리 문자 난수 생성
                    char randomchar = (char)(random.nextInt(26) + 97);// 97~122 // 소문자 a -> z 중 하나 난수 발생
                    authkey.append( randomchar ); // 생성된 문자 난수들을 하나씩 연결 -> 문자열 만들기
                }
                // 인증코드 전달
                html.append( "<a href='http://localhost:8081/member/email/"+authkey+"/"+memberDto.getMid()+"'>이메일검증</a>");

            html.append("</body></html>");

            // 해당 엔티티의 인증키 저장
            memberEntity.setOauth( authkey.toString() );

            // 회원가입 인증 메일 보내기
            mailsend( memberDto.getMemail() , "EZEN부동산 회원가입 메일인증" ,  html );

            return true; // 회원가입 성공
        }
    }
    @Transactional
    public boolean authsuccess( String authkey , String mid  ) {
//        System.out.println(" 검증번호 : " + authkey + " 회원아이디 : " + mid  );
        // DB 업데이트
        Optional<MemberEntity> optional =  memberRepository.findBymid( mid );
        if( optional.isPresent() ){ // optional 이 null 아니면  [ 회원찾기 성공 ]
            MemberEntity memberEntity = optional.get(); // 해당 엔티티 가져오기
            if( authkey.equals( memberEntity.getOauth() ) ) { // 만약에 인증키 와 엔티티의 인증키 와 동일하면
                memberEntity.setOauth("Local");
                return true; // 성공
            }
        }
        return false; // 실패
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
    // 6. 아이디찾기 [ 이름과 이메일이 동일한 경우 프론트엔드에 표시  ]
    public String idfind( String mname , String memail ){
        String idfind = null;
        // 로직
        Optional<MemberEntity> optional =  memberRepository.findid( mname , memail );
        if( optional.isPresent() ){
            idfind = optional.get().getMid();
        }
        return idfind;
    }
    // 7. 패스워드찾기 [ 아이디와 이메일이 동일한 경우 이메일로 임시 비밀번호(난수)전송
    @Transactional
    public boolean pwfind( String mid , String memail ) {
        Optional<MemberEntity> optional = memberRepository.findpw( mid , memail );
        if( optional.isPresent() ){ // 해당 엔티티를 찾았으면
            // 1. 임시비밀번호 난수 생성한다.
            String tempassword = "";         //            StringBuilder temppassword = new StringBuilder();
            for( int i = 0 ; i<12 ; i++ ) {
                Random random = new Random();
                char rchar = (char) (random.nextInt(58) + 65);
                tempassword += rchar;  //                temppassword.append( rchar );
            }
            System.out.println("임시비밀번호 : " + tempassword );
            // 2. 현재 비밀번호를 임시비밀번호로 변경한다.
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();   // 비크립트 방식의 암호화
            optional.get().setMpassword( passwordEncoder.encode( tempassword) ); // 암호화
            // 3. 변경된 비밀번호를 이메일로 전송한다.
            StringBuilder html = new StringBuilder();    // 메일 내용 구현
                html.append("<html><body>");        // html 시작
                html.append("<div>회원님의 임시 비밀번호</div>");
                html.append("<div>"+ tempassword + "</div>");
                html.append("</body></html>");        //html 끝
                // 메일 전송 메소드 호출
                mailsend( optional.get().getMemail(),  "EZEN부동산 회원 임시 비밀번호" ,  html );
            return true;
        }
        // 해당 엔티티를 못찾았으면
        return false;
    }

}
















package ezenweb.config;

import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 해당 클래스 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {
                                                            // 웹 시큐리티 설정 관련 상속클래스

    // 암호화 제공 [ 특정 필드 암호화 ]

    @Override // 재정의
    protected void configure(HttpSecurity http) throws Exception {
                                        // HTTP(URL) 관련 시큐리티 보안
        http //   http
                .authorizeHttpRequests() // 인증된요청
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/member/info").hasRole("MEMBER")
                .antMatchers("/board/save") .hasRole("MEMBER") // 해당 인증 권한 있을경우 = 멤버에 ROLE 필드 있어야함
                .antMatchers("/room/write").hasRole("MEMBER")
                .antMatchers( "/**" ).permitAll()//  인증이 없어도 요청 가능   = 모든 접근 허용
                .and()
                .formLogin() // 로그인페이지 보안 설정
                .loginPage("/member/login") // 아이디 / 비밀번호를 입력받을 페이지 URL
                .loginProcessingUrl("/member/logincontroller") // 로그일 처리할 URL 정의 -> loadUserByUsername
                .defaultSuccessUrl("/")// 로그인 성공시 이동할 URL
                .usernameParameter("mid") // 로그인시 아이디로 입력받을 변수명 [ 기본값 : user -> mid ]
                .passwordParameter("mpassword")// 로그인시 비밀번호로 입력받을 변수명[ 기본값 : password -> mpassword ]
                .failureUrl("/member/login/error")
                // usernameParameter,passwordParameter   => 변수 name 필드명
                .and()
                .logout()
                .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout")) // 로그인 처리할 URL 정의
                .logoutSuccessUrl("/") // 로그인 성공시
                .invalidateHttpSession( true ) // 세션 초기화
                .and()
                .csrf()  // csrf : 사이트 간 요청 위조 [ 해킹 공격 방법중 하나 ] = 서버에게 요청할수 있는 페이지 제한
                .ignoringAntMatchers("/member/logincontroller") // 로그인
                .ignoringAntMatchers("/member/signup") // 회원가입
                .ignoringAntMatchers("/board/save") // 글작성
                .ignoringAntMatchers("/room/write") // 방등록
                .ignoringAntMatchers("/room/roomlist") // 지도에 표시할 데이터 요청/응답
                .ignoringAntMatchers("/member/isread") // 메시지 읽음처리 요청
                .ignoringAntMatchers("/member/msgdelete") // 메시지 읽음처리 요청
                .and()
                .exceptionHandling() // 오류페이지 발생시 시큐리티 페이지 전환
                .accessDeniedPage("/error")
                .and()
                .oauth2Login() // oauth2 관련 설정
                .userInfoEndpoint() // 유저 정보가 들어오는 위치
                .userService( memberService );  // 해당 서비스 클래스 로 유저 정보 받는다~ [ loadUser 재정의 ]
        //super.configure(http); // 슈퍼클래스의 기본 설정으로 사용

    } // configure 메소드 end

    // 로그인 보안 서비스
    // 1.
    @Autowired
    private MemberService memberService;  // 회원관련 서비스
    // 2.
    @Override // 인증(로그인) 관리 메소드
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() ); // [ loadUserByUsername 재정의 ]
                // 인증할 서비스객체                    -> 패스워드 인코딩(   BCrypt 객체로  )
         // super.configure(auth); // 기본값
    }
}

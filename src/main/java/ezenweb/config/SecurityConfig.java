package ezenweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
                .antMatchers( "/**" ).permitAll()  //  인증이 없어도 요청 가능   = 모든 접근 허용
                .and()
                .csrf()  // csrf : 사이트 간 요청 위조 [ 해킹 공격 방법중 하나 ] = 서버에게 요청할수 있는 페이지 제한
                .ignoringAntMatchers("/member/login")
                .ignoringAntMatchers("/member/signup")
                .and()
                .exceptionHandling() // 오류페이지 발생시 시큐리티 페이지 전환
                .accessDeniedPage("/error");
//        super.configure(http); // 슈퍼클래스의 기본 설정으로 사용
    }
}

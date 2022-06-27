package ezenweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA 매핑된 엔티티(테이블)의 변화감지 [ 웹 시작시 JPA 감지 기능 ]
@SpringBootApplication // 스프링부트의 자동 설정 , 스프링 빈(클래스 메모리) 읽기기와 생성을 모두 자동 설정
public class Application {
    public static void main(String[] args) { //  메인 스레드(코드를 읽어주는 역할)
        SpringApplication.run( Application.class  ); // 내장서버(톰캣) 스프링 시작
    }
}

/*
        spring web 5 계층
                1. web 레이어	[ controller, view ]  @Controller / 주요 응답/요청
                2. service 레이어	[ service ] 	@service
               3. Repository 레이어[ repository ]	@Repository  = 생략 가능
                4. Dtos				클래스
                5. domain  , model [ entitiy ] 		@Entitiy


        MVC2 디자인 패턴 (Spring / jpa 사용시 )
                VIEW -----Dto/json----- CONTROLLER -----Dto/json------ SERVICE -------entitiy----- Repository ------Jpa------ DB
                html		     view<-제어->service	         로직(처리기능)
                css					         트랙잭션( SQL처리)
                js

                MVC2 디자인 패턴 ( jsp )
                VIEW -----Dto/json----- CONTROLLER(servlet) ------ Dao ------ DB


*/
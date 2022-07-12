package ezenweb.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration // 해당 클래스를 설정 클래스 으로 사용
@EnableWebSocket    // 웹소켓
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandler webSocketHandler ;
    @Autowired
    private  MsgWebSocketHandler msgWebSocketHandler;
    @Override // 웹소켓핸들러 메소드 재정의
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(  webSocketHandler , "ws/chat").setAllowedOrigins("*")
                .addHandler( msgWebSocketHandler , "ws/message/*").setAllowedOrigins("*");
                            // 사용자정의 웹소켓 핸들러  ,  핸들러 접속 경로  ,  핸들러로 들어올수 있는 도메인 제한
    }
}

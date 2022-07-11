package ezenweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// @Autowired  : 5계층 [ @Service @Controller  @Repository @Entity 등 자동 클래스 스캔 ]
@Component // 5계층 외 클래스에서 @Autowired 사용시 해당 클래스 스캔 위한 @Component  주입
public class WebSocketHandler extends TextWebSocketHandler {
                                                                // 문자 웹소켓 핸들러 클래스   // 웹소켓의 이벤트에 따른 메소드 실행
    @Override // 1. 웹소켓과 연결 되었을때 메소드 재정의
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("서버로 접속했습니다 : " + session );
    }
    @Override // 2. 웹소켓과 연결이 종료 되었을때 메소드 재정의
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("서버에서 나갔습니다. : " + session );
    }
    @Override // 3. 메시지를 받을때 메소드 재정의
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }
}

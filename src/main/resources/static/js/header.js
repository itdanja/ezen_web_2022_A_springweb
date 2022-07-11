
getweather();

// 날씨 크롤링 메소드
function getweather(){
    $.ajax({
        url: "/getweather",
        success: function( object ){
            $("#weatherbox").html(  object.지역명 +"  "+ object.상태 +"  "+ object.온도 +"  "+ object.풍속 +"  "+ object.습도 +"  "+ object.미세먼지  );
        }
    });
}

// 채팅 메소드 - js 열리면 실행되는 메소드
$(document).ready( function(){
    // 1. 익명 닉네임 난수 만들기
        // 1~1000 사이의 난수 생성
    let rand =  Math.floor( Math.random()*1001 );
    let username ="익명"+rand;
    // 3. 클릭이벤트 메소드 정의
    $("#msgbtn").click(  function(){
            send();
      });
    // 2. JS에서 제공하는 websocket 클래스로 websocket 객체 선언
        // 1. [ /ws/chat ] 해당 ( spring :  webSocketHandler path )URL 로 소켓 연결
        // 2. 현재 js가 새로고침[F5] 되면 소켓도 초기화
    let websocket = new WebSocket("ws://localhost:8081/ws/chat");
    websocket.onmessage = onMessage;    // 아래에서 구현한 메소드를 웹소켓 객체에 대입
    websocket.onopen = onOpen;              // 아래에서 구현한 메소드를 웹소켓 객체에 대입
    websocket.onclose = onClose;            // 아래에서 구현한 메소드를 웹소켓 객체에 대입
    // 3. 소켓 연결이 종료 되었을때
    function onClose( ) {
         websocket.send( username +": 님이 나가셨습니다.");
    }
    // 4. 소켓 연결이 되었을때
    function onOpen() {
        websocket.send( username +": 님이 입장했습니다");
    }
    // 5. 메시지 전송
    function send(){
        let msg = $("#msg").val();  // 채팅 입력창에 입력한 데이터 호출
        websocket.send( username+":"+msg);
        $("#msg").val("");
         alert(username+":"+msg);
    }
    // 6. 메시지를 받았을때
    function onMessage( ) { }
});


    // 2. 전송 버튼을 눌렀을때.

//      1. 클릭이벤트 람다식
//    $("#msgbtn").on( "click" , e => {
//        send();
//      });

    // 2. 클릭이벤트 메소드 정의
//    $("#msgbtn").on( "click" , function(){
//            send();
//      });

//    // 3. 클릭이벤트 메소드 정의
//    $("#msgbtn").click(  function(){
//            send();
//      });
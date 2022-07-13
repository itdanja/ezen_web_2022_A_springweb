getisread();
// 안읽은 쪽지 개수 호출 메소드
function getisread(){
    $.ajax({
        url: '/member/getisread',
        success : function( object ){ $("#msgisreadbox").html(object+"+"); }
    });
}

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

// 쪽지 메소드
$(document).ready( function(){

    let mid =  $("#loginmidbox").html();
    if( mid == 'anonymousUser' ){
        return;
    }

    // 문의하기 버튼을 클릭했을때
    $("#roommsgbtn").click( function(){

        let from = mid; // 로그인 된 회원 아이디
        let to = $("#roommid").val(); // 방 등록한 회원 아이디
        let msg = $("#roommsginput").val();
        let jsonmsg = {
            "from" : from ,
            "to" : to ,
            "msg" : msg
        }
        send(  jsonmsg  );
    });
    // 1. js 웹소켓 객체 생성                      // 세션 만으로 회원 구분 X ---> 경로에 회원아이디 추가(식별용)
    let msgwebsocket = new WebSocket("ws://localhost:8081/ws/message/"+mid);
    // 2. 웹소켓객체에 구현된 메소드 저장한다.
    msgwebsocket.onopen = onOpen2;
    msgwebsocket.onclose = onClose2;
    msgwebsocket.onmessage = onMessage2;
    // 3. 각 메소드 구현  [ open close onMessage ]
    function onOpen2(){  }
    function onClose2(){ }
    function onMessage2(){ alert("메시지왔다."); getisread();  }
    function send( jsonmsg ){
        // json형식의 문자열 전송
        msgwebsocket.send(  JSON.stringify(jsonmsg) );
    }
});







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
    }
    // 6. 메시지를 받았을때
    function onMessage( msg ) {
        let data = msg.data; // 받은 메시지의 내용
        let sessionid = data.split(":")[0]; // 보낸사람
        let message = data.split(":")[1]; // 메시지내용

        let html = "";
        // 1. 본인 보낸 메시지 이면
        if( sessionid == username ){
            html += ' <div class="alert alert-primary">';
            html += ' <span>'+sessionid+":"+message;
            html += '</span> ';
            html += ' </div>';

        }else{ // 본인 보낸 메시지가 아니면
            html += ' <div class="alert alert-warning">';
            html += ' <span>'+sessionid+":"+message;
            html += '</span> ';
            html += ' </div>';
        }
        $("#contentbox").append(html); // html 추가
        // 스크롤 최하단으로 이동
        $("#contentbox").scrollTop(  $("#contentbox")[0].scrollHeight );
                // $("#contentbox")[0].scrollHeight : 스크롤의 전체길이
                //  $("#contentbox").scrollTop( ) : 스크롤의 막대의 상단 위치
    }
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
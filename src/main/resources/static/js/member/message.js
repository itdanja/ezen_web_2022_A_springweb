
getfrommsglist();
function getfrommsglist(){
    $.ajax({
        url: '/member/getfrommsglist',
        success: function( object ){
             let html = "<tr><th></th><th>받는사람</th><th>내용</th><th>받은날짜/시간</th></tr>";
             for( let i=0; i<object.length ; i++ ){
                 let color = '';
                 if( object[i].isread  ){ color="#d3d3d3";} // 만약에 읽은 메시지 이면
                 html += '<tr style="color:'+color+'" >';
                 html += '<td ><input type="checkbox"></td>';
                 html += '<td>'+object[i].to+'</td>';
                 html += '<td onclick="msgread('+object[i].msgno+')"  >'+object[i].msg+'</td>';
                 html += '<td>'+object[i].date+'</td>';
                 html += '</tr>';
             }
            $("#frommsgtable").html( html );
        }
    });
}
gettomsglist();
function gettomsglist(){
    $.ajax({
        url: '/member/gettomsglist',
        success: function( object ){
             let html = "<tr><th></th><th>보낸사람</th><th>내용</th><th>받은날짜/시간</th></tr>";
             for( let i=0; i<object.length ; i++ ){
                 let color = '';
                 if( object[i].isread  ){ color="#d3d3d3";} // 만약에 읽은 메시지 이면
                 html += '<tr style="color:'+color+'"  >';
                 // id="중복이름X"   class , name =중복이름O
                 html += '<td ><input name="chkbox"  type="checkbox"  value="'+object[i].msgno+'" onclick="oncheckbox()"></td>';
                 html += '<td >'+object[i].from+'</td>';
                 html += '<td  onclick="msgread('+object[i].msgno+')" >'+object[i].msg+'</td>';
                 html += '<td>'+object[i].date+'</td>';
                 html += '</tr>';
             }
            $("#tomsgtable").html( html );
        }
    });
}
let deletelist = [];    // 삭제할 쪽지의 번호를 저장하는 배열
function msgdelete(){ // 현재 deletelist 배열을 ajax Controller 전달
    alert("선택된 메시지 삭제 합니다. ");
    $.ajax({
        url: '/member/msgdelete' ,
        data : JSON.stringify( deletelist ) ,
        contentType : "application/json" ,
        method : "DELETE" ,
        success: function( object ){
            if( object ){  alert("선택된 메시지 삭제 성공"); getisread(); gettomsglist(); deletelist=[]; }
                                                                        //   1. 안읽은메시지개수 2.받은메시지리스트 3.삭제리스트초기화
        }
    });
}
function oncheckbox(){ // 체크박스를 클릭했을때
     let chkboxlist = $("input[name='chkbox']");   // 1. 모든 체크박스의 객체 호출
     deletelist = [ ];  // 삭제리스트 초기화
     for( let i = 0 ; i<chkboxlist.length; i++ ){ // 2. 반복문 이용한 체크된 박스의 value 값을 deletelist 저장
        if( chkboxlist[i].checked == true){  deletelist.push( chkboxlist[i].value ); }
     }
}

function msgread( msgno ){  // 해당 메시지내용을 클릭했을때 상세정보 모달 출력
    alert(" 읽었으니 메시지 상세 모달 띄우기 ");
    isread( msgno );
}
function isread( msgno ){  // 해당 메시지번호의 읽음처리 업데이트
    $.ajax({
        url: '/member/isread',
        method: 'put',
        data : {"msgno" : msgno } ,
        success: function( object ){  getisread(); gettomsglist(); }
    });
}
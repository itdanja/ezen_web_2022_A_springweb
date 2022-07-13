
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
                 html += '<td ><input type="checkbox"></td>';
                 html += '<td >'+object[i].from+'</td>';
                 html += '<td  onclick="msgread('+object[i].msgno+')" >'+object[i].msg+'</td>';
                 html += '<td>'+object[i].date+'</td>';
                 html += '</tr>';
             }
            $("#tomsgtable").html( html );
        }
    });
}
function msgread( msgno ){
    alert(" 읽었으니 메시지 상세 모달 띄우기 ");
    isread( msgno );
}
function isread( msgno ){
    $.ajax({
        url: '/member/isread',
        method: 'put',
        data : {"msgno" : msgno } ,
        success: function( object ){  getisread(); gettomsglist(); }
    });
}
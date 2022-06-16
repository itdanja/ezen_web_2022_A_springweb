
myroomlist();
// 1. 현재 로그인된 회원 방 리스트 호출 메소드
function myroomlist(){
    $.ajax({
        url: "/room/myroomlist" ,
        method: 'get',
        success: function( roomlist ){
            let html ="";
            for( let i=0; i<roomlist.length; i++ ){
                html +=
                     '<tr>'+
                               '<td><img src="/upload/'+roomlist[i].rimg+'" width="100%"> </td>'+
                                '<td><span> '+roomlist[i].rtitle+'</span></td>'+
                                '<td><span>'+roomlist[i].rdate+'</span></td>'+
                                '<td>'+
                                    '<button onclick="room_update('+roomlist[i].rno+')">수정</button>'+
                                    '<button onclick="room_delete('+roomlist[i].rno+')">삭제</button>'+
                                '</td>'+
                            '</tr>';
            }
            $("#myroomtable").html( html );
        }
    });
}
// 2. 클릭된 방 삭제 메소드
function room_delete( rno ) {
    $.ajax({
        url : "/room/delete",
        method : "DELETE" ,
        data : { "rno" : rno} ,
        success: function( re ){
            if( re == true ){
                alert("삭제 성공");
                myroomlist();
            }else{
                alert("삭제 실패");
            }
        }
    });

}
function room_update( rno ) {
}

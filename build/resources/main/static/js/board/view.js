
board_get();
// 1. 특정 게시물 호출
function board_get(){
    $.ajax({
        url: '/board/getboard' ,
        success : function( board ){
            let html =
                           ' <div>게시물번호 : '+board.bno+'</div>'+
                            '<div>게시물제목 : '+board.btitle+' </div>'+
                            '<div>게시물내용 : '+board.bcontent+' </div>'+
                            '<div>게시물작성일: '+board.bindate+' </div>'+
                            '<div>게시물수정일: '+board.bmodate+' </div>'+
                            '<div>게시물조회수: '+board.bview+' </div>'+
                            '<div>게시물좋아요수: '+board.blike+' </div>'+
                            '<button onclick="board_delete('+board.bno+')"> 삭제 </button>';
            $("#boarddiv").html(html);
        }
    });
}
// 3. D 삭제 처리 메소드
function board_delete( bno ){
      $.ajax({
         url : "/board/delete" ,
         data : { "bno" : bno } ,
         method : "DELETE",
         success : function( re ){
            alert( re );
         }
     });
}
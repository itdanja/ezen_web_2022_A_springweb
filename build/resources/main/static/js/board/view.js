
// 3. D 삭제 처리 메소드
function board_delete(){
      $.ajax({
         url : "/board/delete" ,
         data : { "bno" : bno } ,
         method : "DELETE",
         success : function( re ){
            alert( re );
         }
     });
}
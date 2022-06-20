
board_list();

// 1. C 쓰기 처리 메소드
function board_save(){
    let form = $("#saveform")[0];
    let formdata = new FormData( form );

    $.ajax({
        url : "/board/save" ,
        data : formdata ,
        method : "POST",
        processData : false ,
        contentType : false ,
        success : function( re ){
            alert( re );
        }
    });
}
// 2. R 출력 처리 메소드
function board_list(){
        $.ajax({
            url : "/board/getboardlist" ,
            method : "GET",
            success : function( boardlist ){
                console.log(boardlist);
                let html = $("#boardtable").html();

                for( let i = 0 ; i<boardlist.length ; i++ ){
                    html +=
                            '<tr>'+
                                    '<td>'+boardlist[i].bno+'</td> '+
                                    '<td>'+boardlist[i].btitle+'</td> '+
                                    '<td>'+boardlist[i].bdate+'</td>'+
                                    '<td>'+boardlist[i].bview+'</td>'+
                                    '<td>'+boardlist[i].blike+'</td>'+
                             '</tr>';
                }
                $("#boardtable").html( html );
            }
        });
}
// 3. U 수정 처리 메소드
function board_update(){
    alert("수정합니다..");
        $.ajax({
            url : "/board/update" ,
            data : { } ,
            method : "PUT",
            success : function( re ){
                alert( re );
            }
        });
}
// 3. D 삭제 처리 메소드
function board_delete(){
     alert("삭제합니다.");
      $.ajax({
         url : "/board/delete" ,
         data : { } ,
         method : "DELETE",
         success : function( re ){
            alert( re );
         }
     });
}
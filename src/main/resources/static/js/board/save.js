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
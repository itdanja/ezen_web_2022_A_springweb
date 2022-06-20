// 3. U 수정 처리 메소드
function board_update(){
        let form = $("#updateform")[0];
        let formdata = new FormData( form );
        $.ajax({
            url : "/board/update" ,
            data : formdata ,
            method : "PUT",
            processData : false ,
            contentType : false ,
            success : function( re ){
                alert( re );
            }
        });
}
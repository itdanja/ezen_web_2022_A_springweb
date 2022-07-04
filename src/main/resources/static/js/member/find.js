
function idfind(){
    $.ajax({
        url: "/member/idfind",
        data : { "mname" : $("#idmname").val() , "memail" : $("#idmemail").val()  }  ,
        success: function( re ){
            alert( re );
        }
    });
}
function pwfind(){
    $.ajax({
        url: "/member/pwfind",
        data : { "mid" : $("#pwmname").val() , "memail" : $("#pwmemail").val()  }  ,
        success: function( re ){
            alert( re );
        }
    });
}
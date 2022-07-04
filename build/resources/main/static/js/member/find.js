
function idfind(){
    $.ajax({
        url: "/member/idfind",
        data : { "mname" : $("#idmname").val() , "memail" : $("#idmemail").val()  }  ,
        success: function( re ){
            if( re == "") {
                alert("동일한 회원정보가 없습니다.");
            }else{
                $("#findidbox").html("검색된 아이디 : " + re);
                $("#findidbox").css("display","block");
            }
        }
    });
}
function pwfind(){
    $.ajax({
        url: "/member/pwfind",
        data : { "mid" : $("#pwmname").val() , "memail" : $("#pwmemail").val()  }  ,
        success: function( re ){
            if( re == true ) {
                alert("해당 이메일로 임시비밀번호 전송.");
            }else{
                 alert("동일한 회원정보가 없습니다.");
            }
        }
    });
}
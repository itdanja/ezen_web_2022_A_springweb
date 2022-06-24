function login(){
    $.ajax({
        url: "/member/logincontroller",
        method: "POST",
        data : { "mid" : $("#mid").val()    , "mpassword" : $("#mpassword").val()   } ,
        success: function( re ){
                alert( re );
                if( re == true ){
                    alert("로그인성공");
                    location.href = "/"; // 메인페이지로 매핑
                }else{
                    alert("로그인실패");
                }
        }
    });
}
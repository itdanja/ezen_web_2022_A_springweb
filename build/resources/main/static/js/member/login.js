function login(){
    // 유효성검사 :  이메일 인증 여부
    $.ajax({
        url: '/member/authmailcheck' ,
        data : { mid : $("#mid").val() },
        success: function( re ){
            if( re == 1 ){
                $("#loginform").submit(); // 폼전송
            } else if( re == 2 || re == 3 ){
                alert("간편 로그인을 이용해주세요");
            }else{
                alert("이메일 인증후 로그인이 가능합니다.");
            }
        }
    });
}
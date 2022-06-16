function signup(){
    // 폼 객체화
    let form = $("#signupform")[0];
    let formdata = new FormData( form);
    // 폼 전송
    $.ajax({
        url: "/member/signup",
        method: "POST",
        data: formdata,
        contentType: false ,
        processData: false ,
        success: function( re ){
            alert(re);
            if( re == true ){
                alert("회원가입 성공");
                location.href="/member/login";
            }
            else{
                alert("회원가입 실패 [ 서비스오류 : 관리자에게 문의 ] ");
            }
        }
    });
}
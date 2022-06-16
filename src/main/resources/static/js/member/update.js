function update(){
    $.ajax({
        url: '/member/update',
        data : { "mname" : $("#mname").val() },
        method : "put",
        success: function( re ){
            alert( re );
        }
    });
}
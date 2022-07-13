
getfrommsglist();
function getfrommsglist(){
    $.ajax({
        url: '/member/getfrommsglist',
        success: function( object ){
            console.log( object );
        }
    });
}

gettomsglist();
function gettomsglist(){
    $.ajax({
        url: '/member/gettomsglist',
        success: function( object ){
            console.log( object );
        }
    });
}
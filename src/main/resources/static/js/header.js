
getweather();

function getweather(){
    $.ajax({
        url: "/getweather",
        success: function( re ){
            console.log( re );
        }
    });
}
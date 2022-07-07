
getweather();

function getweather(){
    $.ajax({
        url: "/getweather",
        success: function( object ){

            $("#weatherbox").html(  object.지역명 +"  "+ object.상태 +"  "+ object.온도 +"  "+ object.풍속 +"  "+ object.습도 +"  "+ object.미세먼지  );

        }
    });
}
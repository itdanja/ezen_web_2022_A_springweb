
getnews();

function getnews(){
    $.ajax({
        url: '/getnews',
        success: function( jsonarray ){

            let html  = "";
            for( let i = 0; i < jsonarray.length; i++ ){

            html +=
                       ' <div class="col-md-5 m-3">'+
                         '   <div class="row">'+
                              '  <div class="col-md-5">'+
                                    '<a target="_blank" href="'+ jsonarray[i].링크+'">'+
                                         '<img src="'+ jsonarray[i].사진+'" width="100%">'+
                                    '</a>'+
                               '</div>'+
                                '<div class="col-md-7">'+
                                   '<span class="news_title">'+jsonarray[i].내용+'</span>'+
                                '</div>'+
                           '</div>'+
                        '</div>';
            }

            $("#newsbox").html( html );
        }
    });
}
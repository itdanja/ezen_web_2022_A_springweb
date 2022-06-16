
$.ajax({
    url: "/room/myroomlist" ,
    method: 'get',
    success: function( roomlist ){
        let html ="";
        for( let i=0; i<roomlist.length; i++ ){
            html +=
                 '<tr>'+
                           '<td><img src="/upload/'+roomlist[i].rimg+'" width="100%"> </td>'+
                            '<td><span> '+roomlist[i].rtitle+'</span></td>'+
                            '<td><span>'+roomlist[i].rdate+'</span></td>'+
                            '<td>'+
                                '<button onclick="room_update()">수정</button>'+
                                '<button onclick="room_delete()">삭제</button>'+
                            '</td>'+
                        '</tr>';
        }
        $("#myroomtable").html( html );
    }
})
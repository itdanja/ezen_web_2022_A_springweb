
board_list();
// 2. R 출력 처리 메소드
function board_list(){
        $.ajax({
            url : "/board/getboardlist" ,
            method : "GET",
            success : function( boardlist ){
                console.log(boardlist);
                let html = $("#boardtable").html();

                for( let i = 0 ; i<boardlist.length ; i++ ){
                    html +=
                            '<tr>'+
                                    '<td>'+boardlist[i].bno+'</td> '+
                                    '<td><a href="/board/view">'+boardlist[i].btitle+'<a></td> '+
                                    '<td>'+boardlist[i].bdate+'</td>'+
                                    '<td>'+boardlist[i].bview+'</td>'+
                                    '<td>'+boardlist[i].blike+'</td>'+
                             '</tr>';
                }
                $("#boardtable").html( html );
            }
        });
}
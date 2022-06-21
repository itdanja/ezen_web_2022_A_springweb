
board_list();
category_list();

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
                                    '<td><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].btitle+'<a></td> '+
//                                   '<td><span onclick="view('+boardlist[i].bno+')">'+boardlist[i].btitle+'<span></td>'+
                                    '<td>'+boardlist[i].bindate+'</td>'+
                                    '<td>'+boardlist[i].bview+'</td>'+
                                    '<td>'+boardlist[i].blike+'</td>'+
                                    '<td>'+boardlist[i].mid+'</td>'+
                             '</tr>';
                }
                $("#boardtable").html( html );
            }
        });
}
function category_list(){
    $.ajax({
        url: '/board/getcategorylist' ,
        success : function( categorylist ){
            let html = "";
            for( let i = 0 ; i<categorylist.length ; i++ ){
                html +=
                    '<button onclick="selectcategory('+categorylist[i].cno+')">'+categorylist[i].cname+'</button>';
            }
            $("#categorybox").html( html );
        }
    });
}
function selectcategory( cno ){
    alert( cno );
}









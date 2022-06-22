
board_list( 1 ,"","" ); // 페이지가 처음 열을때 게시물 출력 메소드 호출
category_list( );

// 2. R 출력 처리 메소드 [ cno = 카테고리번호 , key = 검색 키 , keyword = 검색내용 ]
function board_list( cno , key , keyword  ){
        $.ajax({
            url : "/board/getboardlist" ,
            data : {"cno" : cno , "key" : key , "keyword" : keyword } ,
            method : "GET",
            success : function( boardlist ){

                let html = '<tr> <th width="10%">번호</th> <th width="50%">제목</th> <th width="10%">작성일</th> <th width="10%">조회수</th><th width="10%">좋아요수</th><th width="10%">작성자</th></tr>';

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
                    '<button onclick="board_list('+categorylist[i].cno+')">'+categorylist[i].cname+'</button>';
            }
            $("#categorybox").html( html );
        }
    });
}

// 검색 버튼를 눌렀을때
function search(){

    let key = $("#key").val();
    let keyword = $("#keyword").val();
    // 키 와 키워드 입력받음

    board_list( 1 , key , keyword );

}








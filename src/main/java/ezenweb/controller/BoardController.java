package ezenweb.controller;

import ezenweb.dto.BoardDto;
import ezenweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller // 템플릿영역 --> 반환(return) 은 템플릿만 가능하다..
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private HttpServletRequest request;     // 1. 세션 호출을 위한 request 객체 생성

    @Autowired
    private BoardService boardService;     //2. 서비스 호출을 위한 boardService 객체 생성

    //////////////////////////////////////// 1. view 열기[ 템플릿 연결 ] 매핑 //////////////////
    // 1. 게시판 페이지 열기
    @GetMapping("/list")
    public String list(){ return "board/list";}
    // 2. 게시물 개별 조회 열기
    @GetMapping("/view/{bno}")
    public String view( @PathVariable("bno") int bno ) {        // { } 안에서 선언된 변수는 밖에 사용불가
        // 1. 내가 보고 있는 게시물의 번호를 세션 저장
        request.getSession().setAttribute("bno", bno);
        return "board/view";
    }
    // 3. 게시물 수정 페이지 열기
    @GetMapping("/update")
    public String update(){ return "board/update";}
    // 4. 게시물 쓰기 페이지 열기
    @GetMapping("/save")
    public String save() { return  "board/save"; }


    /////////////////////////////////////// 2. service 처리 매핑 ///////////////////////////////////////
    // 1. C : 게시물 저장 메소드
    @PostMapping("/save")
    @ResponseBody   // 템플릿 아닌 객체 반환
    public boolean save(BoardDto boardDto ){

        return boardService.save( boardDto );
    }
    // 2. R : 모든 게시물 출력 메소드
    @GetMapping("/getboardlist")
    public void getboardlist(
            HttpServletResponse response ,
            @RequestParam("cno") int cno ,
            @RequestParam("key") String key ,
            @RequestParam("keyword") String keyword ,
            @RequestParam("page") int page  ){

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(boardService.getboardlist( cno , key , keyword , page ));
        }catch( Exception e ){ System.out.println( e ); }
    }

    // 2. R2 개별 조회 출력 메소드
    @GetMapping("/getboard")
    public void getboard( HttpServletResponse response){
        int bno =  (Integer) request.getSession().getAttribute("bno");

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboard(  bno   ));
        }catch( Exception e ){
            System.out.println( e );
        }
    }
    // 3. U : 수정 메소드
    @PutMapping("/update")
    @ResponseBody
    public boolean update( BoardDto boardDto ){
        int bno =  (Integer) request.getSession().getAttribute("bno");
        boardDto.setBno( bno );
        return boardService.update( boardDto );
    }
    // 4. D : 삭제 메소드
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete( @RequestParam("bno") int bno ){
        return boardService.delete( bno );
    }

    // 5. 카테고리 출력 메소드
    @GetMapping("/getcategorylist")
    public void getcategorylist( HttpServletResponse response){

        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getcategorylist(   ));
        }catch( Exception e){
            System.out.println(e);
        }

    }

}
///////////////////////////////////////
/*
    URL 경로상의 변수 이동  [ method : GET 방식 ]
        1.  <a href="URL/데이터">  <a>
        2.   ajax :   url : "/board/view/"+bno;
               @GetMapping("/view/{변수명}")
                    @PathVariable("변수명")
 */

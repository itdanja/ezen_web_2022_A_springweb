package ezenweb.controller;

import ezenweb.dto.BoardDto;
import ezenweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    //////////////////////////////////////// 1. view 열기[ 템플릿 연결 ] 매핑 //////////////////
    // 1. 게시판 페이지 열기
    @GetMapping("/list")
    public String list(){ return "board/list";}
    // 2. 게시물 개별 조회 페이지
    @GetMapping("/view")  // URL 경로에 변수 =
    public String view( ){
        return "board/view";
    }
    // 3. 게시물 수정 페이지
    @GetMapping("/update")
    public String update(){ return "board/update";}
    // 4. 게시물 쓰기 페이지
    @GetMapping("/save")
    public String save() { return  "board/save"; }
    /////////////////////////////////////// 2. service 처리 매핑 ///////////////////
    // 1. C
    @PostMapping("/save")
    @ResponseBody   // 템플릿 아닌 객체 반환
    public boolean save(BoardDto boardDto ){

        return boardService.save( boardDto );
    }
    // 2. R
    @GetMapping("/getboardlist")
    public void getboardlist( HttpServletResponse response ){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(boardService.getboardlist());
        }catch( Exception e ){ System.out.println( e ); }
    }
    // 3. U
    @PutMapping("/update")
    @ResponseBody
    public boolean update( BoardDto boardDto ){
        return boardService.update( boardDto );
    }
    // 4. D
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete( @RequestParam("bno") int bno ){
        return boardService.delete( bno );
    }
}

package ezenweb.controller;

import ezenweb.dto.HelloDto;
import ezenweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")        // "/" 최상위 경로
    public String index( ){
        // 2. 부동산 관련 뉴스 크롤링
        boardService.getnews();
        // 3. 부동산 시세 크롤링
        boardService.getvalue();

        return "main"; // HTML 파일명
    }

    @GetMapping("/getweather")   // 1. 날씨 크롤링 메소드
    public void getweather(HttpServletResponse response ){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(   boardService.getweather()  );
        }catch( Exception e){}
    }

}

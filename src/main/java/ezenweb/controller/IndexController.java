package ezenweb.controller;

import ezenweb.dto.HelloDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")        // "/" 최상위 경로
    public String index( Model model ){

        // Dto 생성
        HelloDto helloDto = HelloDto.builder()
                .name("유재석")
                .amount(10000)
                .build();
        // 해당 템플릿에 데이터 보내기
        model.addAttribute( "data" , helloDto );

        return "main"; // HTML 파일명
    }

}

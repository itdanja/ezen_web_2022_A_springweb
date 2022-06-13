package ezenweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller         // 해당 클래스가 템플릿영역으로 사용
@RequestMapping("/room") // 해당 클래스의 요청매핑( room )
public class RoomController {

    @GetMapping("/write")
    public String write(){
        return "room/write";
        // templates -> room -> write.html
    }
}

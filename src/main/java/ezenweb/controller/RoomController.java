package ezenweb.controller;

import ezenweb.dto.RoomDto;
import ezenweb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller         // 해당 클래스가 템플릿영역으로 사용
@RequestMapping("/room") // 해당 클래스의 요청매핑( room )
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/write")   // 등록 페이지 이동
    public String write(){
        return "room/write";
        // templates -> room -> write.html
    }
    @PostMapping("/write")   // 등록 처리
    @ResponseBody       // 템플릿이 아닌 객체 반환시 사용되는 어노테이션
    public boolean write_save(
                        @RequestParam("roomname") String roomname ,
                        @RequestParam("x") String x ,
                        @RequestParam("y") String y){
        // DTO 생성
        RoomDto roomDto = RoomDto.builder()
                .roomname(roomname)
                .x(x)
                .y(y)
                .build();
        // 서비스에 dto 전달
        roomService.room_save( roomDto );

        return true;
    }
}

/*
    -----------  @RequestMapping( "경로" ) --------------------------
    @GetMapping          : FIND , GET   [ @RequestMapping( "경로" , method=RequestMethod.GET )  ]
    @PostMapping         :  SAVE            [ @RequestMapping( "경로" , method=RequestMethod.POST ) ]
    @PutMapping          : UPDATE         [ @RequestMapping( "경로" , method=RequestMethod.PUT ) ]
    @ DeleteMapping       : DELETE      [ @RequestMapping( "경로" , method=RequestMethod.DELETE ) ]
 */



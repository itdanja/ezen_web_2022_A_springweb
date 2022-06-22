package ezenweb.controller;

import ezenweb.dto.BoardDto;
import ezenweb.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardControllerTest {

    @Autowired
    private HttpServletRequest request;     // 1. 세션 호출을 위한 request 객체 생성

    @Autowired
    private BoardService boardService;     //2. 서비스 호출을 위한 boardService 객체 생성

    @Test
    void list() {
    }

    @Test
    void view() {
    }

    @Test
    void update() {
    }

    @Test
    void save( ) {
        BoardDto boardDto = BoardDto.builder()
                .btitle("aaa")
                .bcontent("aaaa")
                .build();
         boardService.save( boardDto );
    }

    @Test
    void testSave() {
    }

    @Test
    void getboardlist() {

    }

    @Test
    void getboard() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void getcategorylist() {
    }
}
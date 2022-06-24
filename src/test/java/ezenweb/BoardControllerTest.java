package ezenweb;

import ezenweb.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;        // MockMvc클래스 : 스프링에서 MVC 테스트 위한 메소드 제공
                                                        // 1. perform( http요청메소드("URL") ) ;         : POST .GET , UPDATE , DELETE 등
                                                                // 1.  perform().param("변수명" , "데이터")   :   데이터 전송
                                                                // 2.   perform().session( 세션명 ) : 세션 전송
                                                        // 2. MockHttpSession : 테스트 세션 클래스
                                                        // 3. .andDo(print() )  : 테스트 결과를 console 출력
                                                                //   Status = 200 성공 Status = 404 경로문제 Status = 5x 서버내부문제(java코드)
                                                                // MockHttpServletRequest:
                                                                // MockHttpServletResponse:
    // 1. 게시판 열기 테스트
    @Test
    void testlist() throws Exception {
        mvc.perform( get("/board/list") ).andDo(print() );
    }
    // 2. 게시판 개별 조회 페이지 열기 테스트
    @Test
    void testview() throws Exception {
        mvc.perform(get("/board/view/1")).andDo(print());
    }
    // 3. 게시판 수정 페이지 열기 테스트
    @Test
    void testupdate() throws Exception {
        mvc.perform(get("/board/update") ).andDo(print());
    }
    // 4. 게시판 쓰기 페이지 열기 테스트
    @Test
    void testsave() throws Exception {

        mvc.perform(get("/board/save")).andDo(print());
    }

    ////////////////////////////////////////////////////
    // 게시물 작성 테스트
//    @Test
//    void testsaveservice(  ) throws Exception {
//        // 변수 전달 테스트
//            // http요청메소드("URL").param("필드명",데이터)
//        // 세션 전달 테스트
//            //   MockHttpSession 클래스
//            // http요청메소드("URL").session( 세션객체명 );
//
//        LoginDto loginDto = LoginDto.builder()
//                .mno(1)
//                .mid("qweqwe")
//                .mname("qweqwe")
//                .build();
//        MockHttpSession mockHttpSession = new MockHttpSession();
//        mockHttpSession.setAttribute("login", loginDto);
//
//        mvc.perform( post("/board/save")
//                                .param("btitle" , "테스트제목")
//                                .param("bcontent","테스트내용")
//                                .param("category","자유게시판")
//                                .session( mockHttpSession ) )
//                .andDo(print());
//    }

    // 모든 게시물 호출 테스트
    @Test
    void testgetboardlist() throws Exception{
        mvc.perform( get("/board/getboardlist")
                                    .param("cno" , "1")
                                    .param("key" , "")
                                    .param("keyword" , "")
                                    .param("page" , "0")
                            ).andDo(print() );
    }
    // 게시물 검색 테스트
    @Test
    void testserch() throws Exception{
        // 제목에 '하' 가 포함된 검색 테스트
        mvc.perform( get("/board/getboardlist")
                                .param("cno" , "1")
                                .param("key" , "btitle")
                                .param("keyword" , "하")
                                .param("page" , "0")
                        ).andDo(print() );
    }
    // 게시물 개별 조회 테스트
    @Test
    void testgetboard() throws Exception{

        // 1번 게시물 조회 테스트
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("bno" , 1);

        mvc.perform( get("/board/getboard")
                .session(mockHttpSession) )
                .andDo(print());
    }

    // 특정 게시물 수정 테스트
    @Test
    void testupdateservice() throws Exception{
        // 1번 게시물 수정 테스트
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("bno" , 1);

        mvc.perform( put("/board/update")
                                .param("btitle" , "수정테스트제목")
                                .param("bcontent", "수정테스트내용")
                                .session(mockHttpSession) )
                            .andDo(print() );
    }

    // 특정 게시물 삭제 테스트
    @Test
    void testdeleteservice() throws Exception{
        // 1번 게시물 삭제 테스트
        mvc.perform( delete("/board/delete")
                            .param("bno","1"))
                            .andDo( print() );

    }

    // 카테고리 출력 테스트
    @Test
    void testgetcategorylist() throws Exception{
        mvc.perform( get("/board/getcategorylist") )
                .andDo( print() );
    }

















}

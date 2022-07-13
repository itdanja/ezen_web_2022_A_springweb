package ezenweb.controller;

import ezenweb.dto.HelloDto;
import ezenweb.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller // 템플릿 영역
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;  // member 서비스 객체 선언

    // 이메일인증여부 확인
    @GetMapping("/authmailcheck")
    @ResponseBody
    public int authmailcheck(@RequestParam("mid") String mid ){
        int reulst = memberService.authmailcheck( mid );
        return reulst;
    }

    // 아이디/비밀번호 찾기 페이지 이동 패핑
    @GetMapping("/find")
    public String find(){ return "member/find"; }

    // 아이디 찾기 ( oauth2 회원 제공X )
    @GetMapping("/idfind")
    @ResponseBody
    public String idfind( @RequestParam("mname") String mname ,
                          @RequestParam("memail") String memail){
        String idfind =  memberService.idfind( mname , memail );
        return idfind;
    }
    @GetMapping("/pwfind")
    @ResponseBody
    public Boolean pwfind( @RequestParam("mid") String mid ,
                          @RequestParam("memail") String memail){
        Boolean result =  memberService.pwfind( mid , memail );
        return result;
        // 기본자료형 vs 클래스명
        //   int            vs  Integer     사용용도는 동일하다.. 차이( 메소드 차이 )
    }


    // 1. 로그인 페이지 이동 매핑
    @GetMapping("/login")
    public String login( ){
        return "member/login";
    }

    // 회원이 이메일받았을때 검증버튼을 누르면 들어오는 매핑
    @GetMapping("/email/{authkey}/{mid}")
    public String signupemail( @PathVariable("authkey") String authkey , @PathVariable("mid") String mid){
        // @PathVariable : 경로상(URL) 변수 요청

        // 이메일 검증 처리
        boolean result =  memberService.authsuccess( authkey , mid );
        if( result ){
            // 인증 성공 화면 전환
            return "member/authsuccess";
        }else{ // 인증 실패 화면 전환
            return "";
        }
    }

    // 시큐리티 사용시에는 시큐리티내 로그인 서비스 사용
//    // 3. 로그인 처리 매핑
//    @PostMapping("/login")
//    @ResponseBody
//    public boolean save(@RequestParam("mid") String mid ,
//                        @RequestParam("mpassword") String mpassword ) {
//        return memberService.login( mid , mpassword );
//    }

    // 시큐리티 사용시에는 시큐리티내 로그아웃 서비스 사용
//    // 4. 로그아웃 처리 매핑
//    @GetMapping("/logout")
//    public String logout( ) {
//        memberService.logout();
//        // return "main";  // 타임리프 반환
//         return "redirect:/"; ///
//    }

    // 5. 회원수정 페이지 이동 매핑
    @GetMapping("/update")
    public String update(){
        return "/member/update";
    }
    // 6. 회원수정 처리 매핑
    @PutMapping("/update")
    @ResponseBody
    public boolean update( @RequestParam("mname") String mname) {

        return memberService.update( mname );
    }

    // 7.
    @GetMapping("/info")
    public String info(){
        return "/member/info";
    }
    //8.
    @GetMapping("/myroom")
    public String myroom(){
        return "/member/myroom";
    }

    //9. 삭제 페이지 이동 매핑
    @GetMapping("/delete")
    public String delete( ){ return  "/member/delete"; }
    // 10. 삭제 처리 매핑
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete( @RequestParam("mpassword") String mpassword ){
        return memberService.delete( mpassword);
    }

    // 2. 회원가입 페이지 이동 매핑
    @GetMapping("/signup")
    public String signup(){
        return "/member/write";
    }

    // 3. 회원가입 처리 매핑
    @PostMapping("/signup")
    @ResponseBody
    public boolean  save( MemberDto memberDto ){
        // 서비스 호출
        boolean result =  memberService.signup( memberDto);
        return result;
    }

    /////////////////// 쪽지 ////////////////////////////
    @GetMapping("/getisread") // 1. 안읽은 메시지 처리 컨트롤
    @ResponseBody
    public Integer getisread(){
        return memberService.getisread();
    }
    @GetMapping("/message") // 2. 메시지 html 열기 컨트롤
    public String message( ){ return  "member/message"; }
    @GetMapping("/getfrommsglist")  // 3. 보낸 메시지 리스트 출력 처리 컨트롤
    public void getfrommsglist( HttpServletResponse response ){
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print( memberService.getfrommsglist() );
        }catch( Exception e){ System.out.println(e);  }
    }
    @GetMapping("/gettomsglist") // 3. 받은 메시지 리스트 출력 처리 컨트롤
    public void gettomsglist( HttpServletResponse response ){
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print( memberService.gettomsglist() );
        }catch( Exception e){ System.out.println(e); }
    }
    @PutMapping("/isread")          // 4. 읽음 처리
    @ResponseBody
    public boolean isread( @RequestParam("msgno") int msgno ){
        return memberService.isread( msgno);
    }
    @DeleteMapping("/msgdelete") // 5.선택된 메시지 삭제 처리
    @ResponseBody
    public boolean msgdelete(
            @RequestBody List<Integer> deletelist ){
        return memberService.msgdelete( deletelist );
    }

}













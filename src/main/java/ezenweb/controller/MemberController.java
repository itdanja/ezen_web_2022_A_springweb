package ezenweb.controller;

import ezenweb.dto.HelloDto;
import ezenweb.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // 템플릿 영역
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;  // member 서비스 객체 선언

    // 1. 로그인 페이지 이동 매핑
    @GetMapping("/login")
    public String login( ){
        return "/member/login";
    }

//    // 3. 로그인 처리 매핑
//    @PostMapping("/login")
//    @ResponseBody
//    public boolean save(@RequestParam("mid") String mid ,
//                        @RequestParam("mpassword") String mpassword ) {
//        return memberService.login( mid , mpassword );
//    }

    // 4. 로그아웃 처리 매핑
    @GetMapping("/logout")
    public String logout( ) {
        memberService.logout();
        // return "main";  // 타임리프 반환
         return "redirect:/"; ///
    }

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

}













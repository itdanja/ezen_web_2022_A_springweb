package ezenweb.controller;

import ezenweb.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    //
    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestParam("mid") String mid ,
                        @RequestParam("mpassword") String mpassword ) {
        return memberService.login( mid , mpassword );
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













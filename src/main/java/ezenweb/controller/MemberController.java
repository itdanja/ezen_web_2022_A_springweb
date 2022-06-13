package ezenweb.controller;

import ezenweb.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 템플릿 영역
public class MemberController {

    // 1. 로그인 페이지 이동 매핑
    @GetMapping("/login")
    public String login( ){
        return "login";
    }

    @Autowired
    MemberService memberService;  // member 서비스 객체 선언

    // 2. 회원가입 페이지 이동 매핑
    @GetMapping("/signup")
    public String signup( ){

        // DTO 생성
        MemberDto memberDto =
                MemberDto.builder()
                        .mid("qweqwe")
                        .mpasswrd("qweqwe")
                        .mname("qweqwe")
                        .build();

        // 서비스 호출
        memberService.signup( memberDto);
        return "signup";
    }

}

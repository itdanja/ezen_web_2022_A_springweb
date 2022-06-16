package ezenweb.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginDto {  // 로그인 세션에 넣을 Dto 생성

    private int mno;
    private String mid;
    private String mname;

}

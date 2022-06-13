package ezenweb.dto;

import lombok.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class MemberDto {

    // 필드
    private int mno;
    private String mid;
    private String mpasswrd;
    private String mname;

}

package ezenweb.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder    // 생성자 사용 규칙 X ---> 생성자 만드는데 안전성 보장 [ 인수 순서 , 개수 , NULL/0 등등 ]
public class HelloDto {

    // 필드
    private String name;
    private int amount;

    // 생성자 [ 빈생성자 = @NoArgsConstructor  /  풀생성자 = @AllArgsConstructor]

    // get,set메소드 -> @Getter , @Setter

    // toString -> @ToString

}

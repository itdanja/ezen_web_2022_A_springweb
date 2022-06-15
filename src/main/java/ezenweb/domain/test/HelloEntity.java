package ezenweb.domain.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity // JPA :  DB내 테이블과 매핑(연결)
@Getter// 롬복
@NoArgsConstructor // 롬복
public class HelloEntity {

    @Id // JPA : pk
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // jpa : autokey
    private Long id;

            // varchar(255)          length = 필드길이 , nullable = null포함여부
    @Column(length = 500 , nullable = false)    // jpa : Column( 속성명 = 값 , 속성명 = 값 )
    private String title;

            // columnDefinition = "TEXT" : 긴글 자료형
    @Column( columnDefinition = "TEXT" , nullable = false)
    private  String content;

    private String author;

}

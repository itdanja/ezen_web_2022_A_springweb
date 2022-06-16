package ezenweb.domain.member;

import lombok.*;

import javax.persistence.*;
import java.sql.Connection;

@Entity
@Table(name="member")
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpasswrd;
    private String mname;


}

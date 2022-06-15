package ezenweb.domain.member;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpasswrd;
    private String mname;

}

package ezenweb.domain;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
public class RoomEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rno;
    private String roomname;
    private String x;
    private String y;

}

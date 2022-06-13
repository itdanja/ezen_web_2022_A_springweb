package ezenweb.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RoomDto {

    private String roomname;
    private String x;
    private String y;

}

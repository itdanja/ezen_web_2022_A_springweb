package ezenweb.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RoomDto {

    private String rname;
    private String x;
    private String y;
    private List<MultipartFile> rimg;

    // MultipartFile : 첨부파일 저장할수 있는 인터페이스

}

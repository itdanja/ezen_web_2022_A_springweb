package ezenweb.service;

import ezenweb.domain.RoomEntity;
import ezenweb.domain.RoomRepository;
import ezenweb.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // 1. 룸 저장
    public boolean room_save(RoomDto roomDto){
        // dto -> entitiy
        RoomEntity roomEntity = RoomEntity.builder()
                .roomname( roomDto.getRoomname() )
                .x( roomDto.getX())
                .y( roomDto.getY() )
                .build();
        // 저장
        roomRepository.save( roomEntity );
        return true;
    }

}

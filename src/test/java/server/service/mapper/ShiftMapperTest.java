package server.service.mapper;

import org.junit.jupiter.api.Test;
import server.domain.Shift;
import server.dto.ShiftDTO;

import java.sql.Time;
import java.time.LocalTime;

class ShiftMapperTest {

    ShiftMapper mapper = ShiftMapper.instance;

    @Test
    void toDTO() {
        Shift shift = new Shift();
        shift.setId(1);
        shift.setName("Name");
        shift.setBeginTime(Time.valueOf(LocalTime.now()));
        shift.setEndTime(Time.valueOf(LocalTime.now()));
        ShiftDTO shiftDTO = mapper.toDTO(shift);
        System.out.println(shiftDTO.toString());
    }

    @Test
    void toEntity() {
        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setId(1);
        shiftDTO.setName("Name");
        shiftDTO.setBeginTime(LocalTime.now());
        shiftDTO.setEndTime(LocalTime.now());
        Shift shift = mapper.toEntity(shiftDTO);
        System.out.println(shift.toString());
    }
}
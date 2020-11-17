package server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.domain.Shift;
import server.dto.ShiftDTO;

import java.sql.Time;
import java.time.LocalTime;

@Mapper
public abstract class ShiftMapper {

    public static final ShiftMapper instance =
            Mappers.getMapper(ShiftMapper.class);

    @Mapping(source = "beginTime", target = "beginTime", qualifiedByName =
            "toLocalTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName =
            "toLocalTime")
    public abstract ShiftDTO toDTO(Shift shift);

    @Mapping(source = "beginTime", target = "beginTime", qualifiedByName =
            "toTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName =
            "toTime")
    public abstract Shift toEntity(ShiftDTO shiftDTO);

    Time toTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    LocalTime toLocalTime(Time time) {
        return time.toLocalTime();
    }
}

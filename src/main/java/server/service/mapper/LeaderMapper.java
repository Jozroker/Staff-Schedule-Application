package server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.domain.Leader;
import server.dto.LeaderDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public abstract class LeaderMapper {

    public static final LeaderMapper instance =
            Mappers.getMapper(LeaderMapper.class);

    @Mapping(source = "birthdayDate", target = "birthdayDate",
            qualifiedByName = "toLocalDate")
    public abstract LeaderDTO toDTO(Leader leader);

    @Mapping(source = "birthdayDate", target = "birthdayDate",
            qualifiedByName = "toDate")
    public abstract Leader toEntity(LeaderDTO leaderDTO);

    LocalDate toLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

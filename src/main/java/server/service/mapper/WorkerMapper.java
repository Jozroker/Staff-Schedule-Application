package server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.domain.Position;
import server.domain.Shift;
import server.domain.Unit;
import server.domain.Worker;
import server.dto.WorkerDTO;
import server.error.ResourceNotFoundException;
import server.repository.PositionRepository;
import server.repository.ShiftRepository;
import server.repository.UnitRepository;
import server.repository.impl.PositionRepositoryImpl;
import server.repository.impl.ShiftRepositoryImpl;
import server.repository.impl.UnitRepositoryImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class WorkerMapper {

    public static final WorkerMapper instance =
            Mappers.getMapper(WorkerMapper.class);

    private static final PositionRepository pr =
            PositionRepositoryImpl.getInstance();
    private static final UnitRepository ur = UnitRepositoryImpl.getInstance();
    private static final ShiftRepository sr = ShiftRepositoryImpl.getInstance();

    @Mapping(source = "birthdayDate", target = "birthdayDate",
            qualifiedByName = "toLocalDate")
    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "shift", target = "shiftId", qualifiedByName =
            "toShiftId")
    @Mapping(source = "positions", target = "positionIds", qualifiedByName =
            "toPositionIds")
    @Mapping(source = "units", target = "unitIds", qualifiedByName =
            "toUnitIds")
    @Mapping(source = "shift.name", target = "shiftName")
//    @Mapping(target = "sequenceNumber", ignore = true)
//    @Mapping(target = "changeBtn", ignore = true)
//    @Mapping(target = "deleteBtn", ignore = true)
    public abstract WorkerDTO toDTO(Worker worker);

    @Mapping(source = "birthdayDate", target = "birthdayDate",
            qualifiedByName = "toDate")
    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "shiftId", target = "shift", qualifiedByName = "toShift")
    @Mapping(source = "positionIds", target = "positions", qualifiedByName =
            "toPosition")
    @Mapping(source = "unitIds", target = "units", qualifiedByName =
            "toUnit")
    public abstract Worker toEntity(WorkerDTO workerDTO);

    LocalDate toLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    Set<Position> toPosition(Set<Integer> positionIds) {
        return positionIds.stream().map(id -> pr.findById(id)
                .orElseThrow(ResourceNotFoundException::new))
                .collect(Collectors.toSet());
    }

    Set<Integer> toPositionIds(Set<Position> positions) {
        return positions.stream().map(Position::getId).collect(Collectors.toSet());
    }

    Set<Unit> toUnit(Set<Integer> unitIds) {
        return unitIds.stream().map(id -> ur.findById(id)
                .orElseThrow(ResourceNotFoundException::new))
                .collect(Collectors.toSet());
    }

    Set<Integer> toUnitIds(Set<Unit> units) {
        return units.stream().map(Unit::getId).collect(Collectors.toSet());
    }

    Integer toShiftId(Shift shift) {
        return shift.getId();
    }

    Shift toShift(Integer id) {
        Shift shift;
        Optional<Shift> opt = sr.findById(id);
        if (opt.isPresent()) {
            shift = opt.get();
        } else {
            throw new ResourceNotFoundException();
        }
        return shift;
    }
}

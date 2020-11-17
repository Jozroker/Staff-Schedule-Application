package server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.domain.Position;
import server.dto.PositionDTO;

import java.math.BigDecimal;

@Mapper
public abstract class PositionMapper {

    public static final PositionMapper instance =
            Mappers.getMapper(PositionMapper.class);

    @Mapping(source = "salary", target = "salary", qualifiedByName =
            "toDouble")
    @Mapping(source = "allowance", target = "allowance", qualifiedByName =
            "toDouble")
    public abstract PositionDTO toDTO(Position position);

    @Mapping(source = "salary", target = "salary", qualifiedByName =
            "toBigDecimal")
    @Mapping(source = "allowance", target = "allowance", qualifiedByName =
            "toBigDecimal")
    public abstract Position toEntity(PositionDTO positionDTO);

    double toDouble(BigDecimal number) {
        return number.doubleValue();
    }

    BigDecimal toBigDecimal(double number) {
        return BigDecimal.valueOf(number);
    }
}

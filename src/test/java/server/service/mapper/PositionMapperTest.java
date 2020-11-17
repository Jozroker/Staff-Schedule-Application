package server.service.mapper;

import org.junit.jupiter.api.Test;
import server.domain.Position;
import server.dto.PositionDTO;

import java.math.BigDecimal;

class PositionMapperTest {

    PositionMapper mapper = PositionMapper.instance;

    @Test
    void toDTO() {
        Position position = new Position();
        position.setId(1);
        position.setName("Name");
        position.setSalary(new BigDecimal("112.4"));
        position.setAllowance(new BigDecimal("10"));
        PositionDTO positionDTO = mapper.toDTO(position);
        System.out.println(positionDTO.toString());
    }

    @Test
    void toEntity() {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setId(1);
        positionDTO.setName("Name");
        positionDTO.setSalary(112.4);
        positionDTO.setAllowance(10);
        Position position = mapper.toEntity(positionDTO);
        System.out.println(positionDTO.toString());
    }
}
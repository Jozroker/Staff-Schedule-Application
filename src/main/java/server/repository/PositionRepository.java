package server.repository;

import server.domain.Position;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PositionRepository extends CrudRepository<Position, Integer> {

    Optional<Position> findByName(String name);

    List<Position> findBySalary(BigDecimal min, BigDecimal max);
}

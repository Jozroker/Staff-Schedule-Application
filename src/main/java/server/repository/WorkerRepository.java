package server.repository;

import server.domain.Position;
import server.domain.Shift;
import server.domain.Unit;
import server.domain.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends CrudRepository<Worker, Integer> {

    List<Worker> findByLastName(String lastName);

    Optional<Worker> findByPhone(String phone);

    List<Worker> findByShift(Shift shift);

    List<Worker> findByStage(Integer min, Integer max);

    List<Worker> findByUnit(Unit unit);

    List<Worker> findByPosition(Position position);

}

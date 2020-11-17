package server.repository;

import server.domain.Leader;
import server.domain.Unit;
import server.domain.Worker;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends CrudRepository<Unit, Integer> {

    Optional<Unit> findByName(String name);

    List<Unit> findByLeader(Leader leader);

    List<Unit> findByWorker(Worker worker);
}

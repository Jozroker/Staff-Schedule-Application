package server.repository;

import server.domain.Leader;

import java.util.List;
import java.util.Optional;

public interface LeaderRepository extends CrudRepository<Leader, Integer> {

    Optional<Leader> findByPhone(String phone);

    List<Leader> findByLastName(String lastName);
}

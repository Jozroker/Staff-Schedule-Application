package server.service;

import server.dto.LeaderDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.error.ResourceNotFoundException;

import java.util.List;

public interface UnitService extends CrudService<UnitDTO, Integer> {

    UnitDTO findByName(String name) throws ResourceNotFoundException;

    List<UnitDTO> findByLeader(LeaderDTO leader);

    List<UnitDTO> findByWorker(WorkerDTO worker);

    List<UnitDTO> findByLeaderAndWorker(LeaderDTO leader, WorkerDTO worker);
}

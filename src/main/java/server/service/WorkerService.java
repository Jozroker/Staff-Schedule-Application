package server.service;

import server.dto.PositionDTO;
import server.dto.ShiftDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.error.NoneRecordsBeFoundException;
import server.error.ResourceNotFoundException;

import java.util.List;

public interface WorkerService extends CrudService<WorkerDTO, Integer> {

    List<WorkerDTO> findByLastName(String lastName) throws NoneRecordsBeFoundException;

    WorkerDTO findByPhone(String phone) throws ResourceNotFoundException;

    List<WorkerDTO> findByParameters(ShiftDTO shift,
                                     Integer min,
                                     Integer max, UnitDTO unit,
                                     PositionDTO position) throws NoneRecordsBeFoundException;
}

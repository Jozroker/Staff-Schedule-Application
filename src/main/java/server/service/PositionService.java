package server.service;

import server.dto.PositionDTO;
import server.error.NoneRecordsBeFoundException;
import server.error.ResourceNotFoundException;

import java.util.List;

public interface PositionService extends CrudService<PositionDTO, Integer> {

    PositionDTO findByName(String name) throws ResourceNotFoundException;

    List<PositionDTO> findBySalary(Double min, Double max) throws NoneRecordsBeFoundException;

}

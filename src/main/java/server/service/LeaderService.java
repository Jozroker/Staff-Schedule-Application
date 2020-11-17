package server.service;

import server.dto.LeaderDTO;
import server.error.NoneRecordsBeFoundException;
import server.error.ResourceNotFoundException;

import java.util.List;

public interface LeaderService extends CrudService<LeaderDTO, Integer> {

    LeaderDTO findByPhone(String phone) throws ResourceNotFoundException;

    List<LeaderDTO> findByLastName(String lastName) throws NoneRecordsBeFoundException;
}

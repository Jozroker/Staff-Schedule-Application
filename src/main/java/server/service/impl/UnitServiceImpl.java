package server.service.impl;

import server.domain.Unit;
import server.dto.LeaderDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.error.ResourceNotFoundException;
import server.repository.UnitRepository;
import server.repository.impl.UnitRepositoryImpl;
import server.service.UnitService;
import server.service.mapper.LeaderMapper;
import server.service.mapper.UnitMapper;
import server.service.mapper.WorkerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitServiceImpl implements UnitService {

    private static UnitService instance;
    private static final UnitRepository ur = UnitRepositoryImpl.getInstance();
    private static final UnitMapper um = UnitMapper.instance;
    private static final LeaderMapper lm = LeaderMapper.instance;
    private static final WorkerMapper wm = WorkerMapper.instance;

    private UnitServiceImpl() {
    }

    public static UnitService getInstance() {
        if (instance == null) {
            instance = new UnitServiceImpl();
        }
        return instance;
    }

    @Override
    public UnitDTO findByName(String name) throws ResourceNotFoundException {
        UnitDTO unitDTO;
        Optional<Unit> unit = ur.findByName(name);
        if (unit.isPresent()) {
            unitDTO = um.toDTO(unit.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return unitDTO;
    }

    @Override
    public List<UnitDTO> findByLeader(LeaderDTO leader) {
        List<Unit> unitList = ur.findByLeader(lm.toEntity(leader));
        List<UnitDTO> listDTO = new ArrayList<>();
        if (!unitList.isEmpty()) {
            for (Unit unit : unitList) {
                listDTO.add(um.toDTO(unit));
            }
        }
        return listDTO;
    }

    @Override
    public List<UnitDTO> findByWorker(WorkerDTO worker) {
        List<Unit> unitList = ur.findByWorker(wm.toEntity(worker));
        List<UnitDTO> listDTO = new ArrayList<>();
        if (!unitList.isEmpty()) {
            for (Unit unit : unitList) {
                listDTO.add(um.toDTO(unit));
            }
        }
        return listDTO;
    }

    @Override
    public List<UnitDTO> findAll() {
        return ur.findAll().stream().map(um::toDTO).collect(Collectors.toList());
    }

    @Override
    public UnitDTO create(UnitDTO unitDTO) {
        Unit newUnit;
        newUnit = ur.save(um.toEntity(unitDTO));
        return um.toDTO(newUnit);
    }

    @Override
    public UnitDTO update(UnitDTO unitDTO) {
        ur.save(um.toEntity(unitDTO));
        return unitDTO;
    }

    @Override
    public UnitDTO findById(Integer id) throws ResourceNotFoundException {
        UnitDTO unitDTO;
        Optional<Unit> unit = ur.findById(id);
        if (unit.isPresent()) {
            unitDTO = um.toDTO(unit.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return unitDTO;
    }

    @Override
    public List<UnitDTO> findByLeaderAndWorker(LeaderDTO leader,
                                               WorkerDTO worker) {
        List<Unit> unitList1 = ur.findByLeader(lm.toEntity(leader));
        List<Unit> unitList2 = ur.findByWorker(wm.toEntity(worker));
        List<UnitDTO> listDTO = new ArrayList<>();
        if (!unitList1.isEmpty()) {
            for (Unit unit : unitList1) {
                listDTO.add(um.toDTO(unit));
            }
            if (!unitList2.isEmpty()) {
                for (Unit unit : unitList2) {
                    listDTO =
                            listDTO.stream().distinct().filter(unitList2::contains).collect(Collectors.toList());
                }
            }
        } else if (!unitList2.isEmpty()) {
            for (Unit unit : unitList2) {
                listDTO.add(um.toDTO(unit));
            }
        }
        return listDTO;
    }

    @Override
    public void delete(UnitDTO unitDTO) {
        ur.delete(um.toEntity(unitDTO));
    }
}

package server.service.impl;

import server.domain.Worker;
import server.dto.PositionDTO;
import server.dto.ShiftDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.error.NoneRecordsBeFoundException;
import server.error.ResourceNotFoundException;
import server.repository.WorkerRepository;
import server.repository.impl.WorkerRepositoryImpl;
import server.service.WorkerService;
import server.service.mapper.PositionMapper;
import server.service.mapper.ShiftMapper;
import server.service.mapper.UnitMapper;
import server.service.mapper.WorkerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkerServiceImpl implements WorkerService {

    private static WorkerService instance;
    private static final WorkerRepository wr =
            WorkerRepositoryImpl.getInstance();
    private static final WorkerMapper wm = WorkerMapper.instance;
    private static final ShiftMapper sm = ShiftMapper.instance;
    private static final UnitMapper um = UnitMapper.instance;
    private static final PositionMapper pm = PositionMapper.instance;

    private WorkerServiceImpl() {
    }

    public static WorkerService getInstance() {
        if (instance == null) {
            instance = new WorkerServiceImpl();
        }
        return instance;
    }

    @Override
    public List<WorkerDTO> findByLastName(String lastName) throws NoneRecordsBeFoundException {
        List<Worker> workerList = wr.findByLastName(lastName);
        List<WorkerDTO> listDTO = new ArrayList<>();
        if (!workerList.isEmpty()) {
            for (Worker worker : workerList) {
                listDTO.add(wm.toDTO(worker));
            }
        } else {
            throw new NoneRecordsBeFoundException();
        }
        return listDTO;
    }

    @Override
    public List<WorkerDTO> findAll() {
        return wr.findAll().stream().map(wm::toDTO).collect(Collectors.toList());
    }

    @Override
    public WorkerDTO findByPhone(String phone) throws ResourceNotFoundException {
        WorkerDTO workerDTO;
        Optional<Worker> worker = wr.findByPhone(phone);
        if (worker.isPresent()) {
            workerDTO = wm.toDTO(worker.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return workerDTO;
    }

    @Override
    public List<WorkerDTO> findByParameters(ShiftDTO shift, Integer min,
                                            Integer max, UnitDTO unit,
                                            PositionDTO position) throws NoneRecordsBeFoundException {
        List<Worker> workerList = wr.findAll();
        List<WorkerDTO> listDTO = new ArrayList<>();
        if (shift != null) {
            List<Worker> workersByShift = wr.findByShift(sm.toEntity(shift));
            workerList =
                    workerList.stream().distinct().filter(workersByShift::contains).collect(Collectors.toList());
        }
        if (unit != null) {
            List<Worker> workersByUnit = wr.findByUnit(um.toEntity(unit));
            workerList =
                    workerList.stream().distinct().filter(workersByUnit::contains).collect(Collectors.toList());
        }
        if (position != null) {
            List<Worker> workersByPosition =
                    wr.findByPosition(pm.toEntity(position));
            workerList =
                    workerList.stream().distinct().filter(workersByPosition::contains).collect(Collectors.toList());
        }
        if (min != 0) {
            List<Worker> workersByStage;
            if (max != 0) {
                workersByStage = wr.findByStage(min, max);
            } else {
                workersByStage = wr.findByStage(min, null);
            }
            workerList =
                    workerList.stream().distinct().filter(workersByStage::contains).collect(Collectors.toList());
        } else if (max != 0) {
            List<Worker> workersByStage = wr.findByStage(null, max);
            workerList =
                    workerList.stream().distinct().filter(workersByStage::contains).collect(Collectors.toList());
        }
        for (Worker worker : workerList) {
            listDTO.add(wm.toDTO(worker));
        }
        if (listDTO.isEmpty()) {
            throw new NoneRecordsBeFoundException();
        }

        return listDTO;
    }

    @Override
    public WorkerDTO create(WorkerDTO worker) {
        Worker newWorker;
        newWorker = wr.save(wm.toEntity(worker));
        return wm.toDTO(newWorker);
    }

    @Override
    public WorkerDTO update(WorkerDTO worker) {
        wr.save(wm.toEntity(worker));
        return worker;
    }

    @Override
    public WorkerDTO findById(Integer id) throws ResourceNotFoundException {
        WorkerDTO workerDTO;
        Optional<Worker> worker = wr.findById(id);
        if (worker.isPresent()) {
            workerDTO = wm.toDTO(worker.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return workerDTO;
    }

    @Override
    public void delete(WorkerDTO workerDTO) {
        wr.delete(wm.toEntity(workerDTO));
    }
}

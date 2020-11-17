package server.service.impl;

import server.domain.Position;
import server.dto.PositionDTO;
import server.error.NoneRecordsBeFoundException;
import server.error.ResourceNotFoundException;
import server.repository.PositionRepository;
import server.repository.impl.PositionRepositoryImpl;
import server.service.PositionService;
import server.service.mapper.PositionMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PositionServiceImpl implements PositionService {

    private static PositionService instance;
    private final PositionRepository pr = PositionRepositoryImpl.getInstance();
    private static final PositionMapper pm = PositionMapper.instance;

    private PositionServiceImpl() {
    }

    public static PositionService getInstance() {
        if (instance == null) {
            instance = new PositionServiceImpl();
        }
        return instance;
    }

    @Override
    public PositionDTO findByName(String name) throws ResourceNotFoundException {
        PositionDTO positionDTO;
        Optional<Position> position = pr.findByName(name);
        if (position.isPresent()) {
            positionDTO = pm.toDTO(position.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return positionDTO;
    }

    @Override
    public PositionDTO create(PositionDTO positionDTO) {
        Position newPosition;
        newPosition = pr.save(pm.toEntity(positionDTO));
        return pm.toDTO(newPosition);
    }

    @Override
    public PositionDTO update(PositionDTO positionDTO) {
        pr.save(pm.toEntity(positionDTO));
        return positionDTO;
    }

    @Override
    public List<PositionDTO> findAll() {
        return pr.findAll().stream().map(pm::toDTO).collect(Collectors.toList());
    }

    @Override
    public PositionDTO findById(Integer id) throws ResourceNotFoundException {
        PositionDTO positionDTO;
        Optional<Position> position = pr.findById(id);
        if (position.isPresent()) {
            positionDTO = pm.toDTO(position.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return positionDTO;
    }

    @Override
    public List<PositionDTO> findBySalary(Double min, Double max) throws NoneRecordsBeFoundException {
        List<Position> positionList = pr.findBySalary(BigDecimal.valueOf(min),
                BigDecimal.valueOf(max));
        List<PositionDTO> listDTO = new ArrayList<>();
        if (!positionList.isEmpty()) {
            for (Position position : positionList) {
                listDTO.add(pm.toDTO(position));
            }
        } else {
            throw new NoneRecordsBeFoundException();
        }
        return listDTO;
    }

    @Override
    public void delete(PositionDTO positionDTO) {
        pr.delete(pm.toEntity(positionDTO));
    }
}
package server.service.impl;

import server.domain.Shift;
import server.dto.ShiftDTO;
import server.error.ResourceNotFoundException;
import server.repository.ShiftRepository;
import server.repository.impl.ShiftRepositoryImpl;
import server.service.ShiftService;
import server.service.mapper.ShiftMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShiftServiceImpl implements ShiftService {

    private static ShiftService instance;
    private static final ShiftRepository sr = ShiftRepositoryImpl.getInstance();
    private final ShiftMapper sm = ShiftMapper.instance;

    private ShiftServiceImpl() {
    }

    public static ShiftService getInstance() {
        if (instance == null) {
            instance = new ShiftServiceImpl();
        }
        return instance;
    }

    @Override
    public ShiftDTO create(ShiftDTO shiftDTO) {
        Shift newShift;
        newShift = sr.save(sm.toEntity(shiftDTO));
        return sm.toDTO(newShift);
    }

    @Override
    public ShiftDTO update(ShiftDTO shiftDTO) {
        sr.save(sm.toEntity(shiftDTO));
        return shiftDTO;
    }

    @Override
    public List<ShiftDTO> findAll() {
        return sr.findAll().stream().map(sm::toDTO).collect(Collectors.toList());
    }

    @Override
    public ShiftDTO findById(Integer id) throws ResourceNotFoundException {
        ShiftDTO shiftDTO;
        Optional<Shift> shift = sr.findById(id);
        if (shift.isPresent()) {
            shiftDTO = sm.toDTO(shift.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return shiftDTO;
    }

    @Override
    public void delete(ShiftDTO shiftDTO) {
        sr.delete(sm.toEntity(shiftDTO));
    }
}

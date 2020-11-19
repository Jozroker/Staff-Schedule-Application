package server.service.impl;

import server.domain.Leader;
import server.dto.LeaderDTO;
import server.error.ResourceNotFoundException;
import server.repository.LeaderRepository;
import server.repository.impl.LeaderRepositoryImpl;
import server.service.LeaderService;
import server.service.mapper.LeaderMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LeaderServiceImpl implements LeaderService {

    private static LeaderServiceImpl instance;
    private final LeaderRepository lr = LeaderRepositoryImpl.getInstance();
    private static final LeaderMapper lm = LeaderMapper.instance;

    private LeaderServiceImpl() {
    }

    public static LeaderServiceImpl getInstance() {
        if (instance == null) {
            instance = new LeaderServiceImpl();
        }
        return instance;
    }

    @Override
    public LeaderDTO findByPhone(String phone) throws ResourceNotFoundException {
        LeaderDTO leaderDTO;
        Optional<Leader> leader = lr.findByPhone(phone);
        if (leader.isPresent()) {
            leaderDTO = lm.toDTO(leader.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return leaderDTO;
    }

    @Override
    public List<LeaderDTO> findByLastName(String lastName) {
        List<Leader> list = lr.findByLastName(lastName);
        List<LeaderDTO> listDTO = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Leader leader : list) {
                listDTO.add(lm.toDTO(leader));
            }
        }
        return listDTO;
    }

    @Override
    public LeaderDTO create(LeaderDTO leaderDTO) {
        Leader newLeader = lr.save(lm.toEntity(leaderDTO));
        return lm.toDTO(newLeader);
    }

    @Override
    public LeaderDTO update(LeaderDTO leaderDTO) {
        lr.save(lm.toEntity(leaderDTO));
        return leaderDTO;
    }

    @Override
    public List<LeaderDTO> findAll() {
        return lr.findAll().stream().map(lm::toDTO).collect(Collectors.toList());
    }

    @Override
    public LeaderDTO findById(Integer id) throws ResourceNotFoundException {
        Optional<Leader> leader = lr.findById(id);
        LeaderDTO leaderDTO;
        if (leader.isPresent()) {
            leaderDTO = lm.toDTO(leader.get());
        } else {
            throw new ResourceNotFoundException();
        }
        return leaderDTO;
    }

    @Override
    public void delete(LeaderDTO leaderDTO) {
        lr.delete(lm.toEntity(leaderDTO));
    }
}

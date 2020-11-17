package server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.domain.Leader;
import server.domain.Unit;
import server.domain.Worker;
import server.dto.UnitDTO;
import server.error.ResourceNotFoundException;
import server.repository.LeaderRepository;
import server.repository.WorkerRepository;
import server.repository.impl.LeaderRepositoryImpl;
import server.repository.impl.WorkerRepositoryImpl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class UnitMapper {

    private static final WorkerRepository wr =
            WorkerRepositoryImpl.getInstance();
    private static final LeaderRepository lr =
            LeaderRepositoryImpl.getInstance();

    public static final UnitMapper instance =
            Mappers.getMapper(UnitMapper.class);

    @Mapping(source = "workerQuantity", target = "workerQty")
    @Mapping(source = "leader", target = "leaderId", qualifiedByName =
            "toLeaderId")
    @Mapping(source = "workers", target = "workerIds", qualifiedByName =
            "toWorkerIds")
    @Mapping(source = "leader.firstName", target = "leaderFirstName")
    @Mapping(source = "leader.lastName", target = "leaderLastName")
    public abstract UnitDTO toDTO(Unit unit);

    @Mapping(source = "workerQty", target = "workerQuantity")
    @Mapping(source = "leaderId", target = "leader", qualifiedByName =
            "toLeader")
    @Mapping(source = "workerIds", target = "workers", qualifiedByName =
            "toWorkers")
    public abstract Unit toEntity(UnitDTO unitDTO);

    Set<Worker> toWorkers(Set<Integer> workerIds) {
        return workerIds.stream().map(id -> wr.findById(id)
                .orElseThrow(ResourceNotFoundException::new))
                .collect(Collectors.toSet());
    }

    Set<Integer> toWorkerIds(Set<Worker> workers) {
        return workers.stream().map(Worker::getId).collect(Collectors.toSet());
    }

    Integer toLeaderId(Leader leader) {
        return leader.getId();
    }

    Leader toLeader(Integer id) {
        Leader leader;
        Optional<Leader> opt = lr.findById(id);
        if (opt.isPresent()) {
            leader = opt.get();
        } else {
            throw new ResourceNotFoundException();
        }
        return leader;
    }
}

package server.service.mapper;

import org.junit.jupiter.api.Test;
import server.domain.Leader;
import server.dto.LeaderDTO;

import java.sql.Date;
import java.time.LocalDate;

class LeaderMapperTest {

    LeaderMapper mapper = LeaderMapper.instance;

    @Test
    void toDTO() {
        Leader leader = new Leader();
        leader.setId(1);
        leader.setBirthdayDate(Date.valueOf(LocalDate.now()));
        leader.setFirstName("First");
        leader.setLastName("Last");
        leader.setFatherName("Father");
        leader.setPhone("0506693793");
        LeaderDTO leaderDTO = mapper.toDTO(leader);
        System.out.println(leaderDTO.toString());
    }

    @Test
    void toEntity() {
        LeaderDTO leaderDTO = new LeaderDTO();
        leaderDTO.setId(1);
        leaderDTO.setBirthdayDate(LocalDate.now());
        leaderDTO.setFirstName("First");
        leaderDTO.setLastName("Last");
        leaderDTO.setFatherName("Father");
        leaderDTO.setPhone("0506693793");
        Leader leader = mapper.toEntity(leaderDTO);
        System.out.println(leader.toString());
    }
}
package server.service.mapper;

import org.junit.jupiter.api.*;
import server.config.EntityManagerClass;
import server.config.ValidationClass;
import server.domain.Worker;
import server.dto.*;
import server.service.*;
import server.service.impl.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkerMapperTest {

    WorkerMapper mapper = WorkerMapper.instance;

    WorkerService ws = WorkerServiceImpl.getInstance();
    UnitService us = UnitServiceImpl.getInstance();
    LeaderService ls = LeaderServiceImpl.getInstance();
    ShiftService ss = ShiftServiceImpl.getInstance();
    PositionService ps = PositionServiceImpl.getInstance();

    private static WorkerDTO worker = new WorkerDTO();
    private static UnitDTO unit = new UnitDTO();
    private static LeaderDTO leader = new LeaderDTO();
    private static ShiftDTO shift = new ShiftDTO();
    private static PositionDTO position = new PositionDTO();
    private static Worker worker2;

    @BeforeAll
    static void createEntities() {
        shift.setName("Name");
        shift.setBeginTime(LocalTime.now());
        shift.setEndTime(LocalTime.now());

        position.setName("Name");
        position.setSalary(112.2);
        position.setAllowance(10);

        leader.setFirstName("First");
        leader.setLastName("Last");
        leader.setFatherName("Father");
        leader.setPhone("0506693793");
        leader.setBirthdayDate(LocalDate.now());

        unit.setName("Name");
        unit.setWorkerQty(1);

        worker.setFirstName("First");
        worker.setLastName("Last");
        worker.setPhoneNumber("0506693793");
        worker.setBirthdayDate(LocalDate.now());
        worker.setStage(4);
    }

    @Test
    @Order(1)
    void create() {
        shift = ss.create(shift);
        position = ps.create(position);
        leader = ls.create(leader);
        unit.setLeaderId(leader.getId());
        unit = us.create(unit);
        worker.addUnit(unit);
        worker.addPosition(position);
        worker.setShiftId(shift.getId());
        worker = ws.create(worker);
    }

    @Test
    @Order(3)
    void toDTO() {
        WorkerDTO workerDTO = mapper.toDTO(worker2);
        System.out.println(workerDTO.toString());
        System.out.println("Validation test: " + ValidationClass.validate(workerDTO));
    }

    @Test
    @Order(2)
    void toEntity() {
        Worker worker3 = mapper.toEntity(worker);
        System.out.println(worker3.toString());
        worker2 = worker3;
    }

    @AfterAll
    static void deleteAll() {
        EntityManager em = EntityManagerClass.getInstance();
        em.getTransaction().begin();
        Query query1 = em.createQuery("delete from Worker w");
        Query query2 = em.createQuery("delete from Unit u");
        Query query3 = em.createQuery("delete from Leader l");
        Query query4 = em.createQuery("delete from Shift s");
        Query query5 = em.createQuery("delete from Position p");
        query1.executeUpdate();
        query2.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        query3.executeUpdate();
        query4.executeUpdate();
        query5.executeUpdate();
        em.getTransaction().commit();
    }
}
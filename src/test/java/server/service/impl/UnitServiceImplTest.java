package server.service.impl;

import org.junit.jupiter.api.*;
import server.config.EntityManagerClass;
import server.dto.*;
import server.error.ResourceNotFoundException;
import server.service.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnitServiceImplTest {

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
    @Order(2)
    void findByName() {
        UnitDTO unitDTO = us.findByName(unit.getName());
        assertDoesNotThrow(ResourceNotFoundException::new);
    }

    @Test
    @Order(3)
    void findByLeader() {
        List<UnitDTO> list = us.findByLeader(leader);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(4)
    void findByWorker() {
        List<UnitDTO> list = us.findByWorker(worker);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(5)
    void findAll() {
        List<UnitDTO> list = us.findAll();
        assertFalse(list.isEmpty());
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
    @Order(6)
    void update() {
        unit.setName("Other Name");
        unit = us.update(unit);
        assertEquals(unit.getName(), "Other Name");
    }

    @Test
    @Order(7)
    void findById() {
        UnitDTO unitDTO = us.findById(unit.getId());
        assertDoesNotThrow(ResourceNotFoundException::new);
        assertEquals(unitDTO.getName(), "Other Name");
    }

    @Test
    @Order(8)
    void delete() {
        us.delete(unit);
        List<UnitDTO> list = us.findAll();
        assertTrue(list.isEmpty());
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
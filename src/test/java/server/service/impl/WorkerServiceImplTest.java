package server.service.impl;

import org.junit.jupiter.api.*;
import server.config.EntityManagerClass;
import server.dto.*;
import server.service.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkerServiceImplTest {

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
    void findByLastName() {
        List<WorkerDTO> list = ws.findByLastName(worker.getLastName());
        assertFalse(list.isEmpty());
        List<WorkerDTO> list1 = ws.findByLastName("Other");
        assertTrue(list1.isEmpty());
    }

    @Test
    @Order(3)
    void findAll() {
        List<WorkerDTO> list = ws.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(4)
    void findByPhone() {
        WorkerDTO workerDTO = ws.findByPhone(worker.getPhoneNumber());
        assertNotEquals(workerDTO.getId(), 0);
    }

    @Test
    @Order(5)
    void findByShift() {
        List<WorkerDTO> list = ws.findByParameters(shift, 0, 0, null, null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(6)
    void findByStage() {
        List<WorkerDTO> list = ws.findByParameters(null, worker.getStage(),
                worker.getStage(), null,
                null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(7)
    void findByUnit() {
        List<WorkerDTO> list = ws.findByParameters(null, 0, 0, unit, null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(8)
    void findByPosition() {
        List<WorkerDTO> list = ws.findByParameters(null, 0, 0, null, position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(11)
    void findByShiftAndStage() {
        List<WorkerDTO> list = ws.findByParameters(shift, worker.getStage(),
                worker.getStage(), null,
                null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(12)
    void findByShiftAndUnit() {
        List<WorkerDTO> list = ws.findByParameters(shift, 0, 0, unit, null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(13)
    void findByShiftAndPosition() {
        List<WorkerDTO> list = ws.findByParameters(shift, 0, 0, null, position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(14)
    void findByStageAndUnit() {
        List<WorkerDTO> list = ws.findByParameters(null, worker.getStage(),
                worker.getStage(), unit,
                null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(15)
    void findByStageAndPosition() {
        List<WorkerDTO> list = ws.findByParameters(null, worker.getStage(),
                worker.getStage(), null,
                position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(16)
    void findByUnitAndPosition() {
        List<WorkerDTO> list = ws.findByParameters(null, 0, 0, unit, position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(17)
    void findByShiftAndStageAndUnit() {
        List<WorkerDTO> list = ws.findByParameters(shift, worker.getStage(),
                worker.getStage(), unit,
                null);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(18)
    void findByShiftAndStageAndPosition() {
        List<WorkerDTO> list = ws.findByParameters(shift, worker.getStage(),
                worker.getStage(), null,
                position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(19)
    void findByShiftAndUnitAndPosition() {
        List<WorkerDTO> list = ws.findByParameters(shift, 0, 0, unit, position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(20)
    void findByStageAndUnitAndPosition() {
        List<WorkerDTO> list =
                ws.findByParameters(null, worker.getStage(),
                        worker.getStage(), unit,
                        position);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(21)
    void findByShiftAndStageAndUnitAndPosition() {
        List<WorkerDTO> list = ws.findByParameters(shift, worker.getStage(),
                worker.getStage(), unit,
                position);
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
    @Order(9)
    void update() {
        shift = ss.create(shift);
        position = ps.create(position);
        leader = ls.create(leader);
        unit.setLeaderId(leader.getId());
        unit = us.create(unit);
        worker.addUnit(unit);
        worker.addPosition(position);
        worker.setShiftId(shift.getId());
        worker = ws.create(worker);

        worker.setFirstName("Other Name");
        worker = ws.update(worker);
        assertEquals(worker.getFirstName(), "Other Name");
    }

    @Test
    @Order(10)
    void findById() {
        WorkerDTO workerDTO = ws.findById(worker.getId());
        assertNotEquals(workerDTO.getId(), 0);
    }

    @Test
    @Order(22)
    void delete() {
        ws.delete(worker);
        List<WorkerDTO> list = ws.findAll();
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
package server.service.impl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import server.config.EntityManagerClass;
import server.dto.ShiftDTO;
import server.service.ShiftService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShiftServiceImplTest {

    ShiftService ss = ShiftServiceImpl.getInstance();
    private static ShiftDTO shift, shift2;

    @Test
    @Order(1)
    void create() {
        shift = new ShiftDTO();
        shift.setName("Shift");
        shift.setBeginTime(LocalTime.now());
        shift.setEndTime(LocalTime.now().plusHours(1));
        System.out.println("First shift:");
        System.out.println(shift.toString());
        assertEquals(shift.getId(), 0);
        shift = ss.create(shift);
        System.out.println("Second shift:");
        System.out.println(shift.toString());
        assertNotEquals(shift.getId(), 0);
    }

    @Test
    @Order(2)
    void update() {
        System.out.println(shift.toString());
        shift.setName("Other Name");
        ss.update(shift);
        System.out.println("Shift update:");
        System.out.println(shift.toString());
    }

    @Test
    @Order(3)
    void findAll() {
        List<ShiftDTO> list = ss.findAll();
        for (ShiftDTO shiftDTO : list) {
            System.out.println(shiftDTO);
        }
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(4)
    void findById() {
        shift2 = ss.findById(shift.getId());
        System.out.println(shift2.toString());
        assertEquals(shift.getId(), shift2.getId());
    }

    @Test
    @Order(5)
    void delete() {
        ss.delete(shift2);
        List<ShiftDTO> list = ss.findAll();
        assertTrue(list.isEmpty());
    }

    @Test
    @Order(6)
    @Transactional
    void deleteAll() {
        EntityManager em = EntityManagerClass.getInstance();
        em.getTransaction().begin();
        create();
        create();
        create();
        Query query = em.createNativeQuery("delete s from Shift s");
        query.executeUpdate();
        em.getTransaction().commit();
        List<ShiftDTO> list = ss.findAll();
        assertTrue(list.isEmpty());
    }
}
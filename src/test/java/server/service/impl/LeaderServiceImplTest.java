package server.service.impl;

import org.junit.jupiter.api.*;
import server.config.EntityManagerClass;
import server.dto.LeaderDTO;
import server.error.ResourceNotFoundException;
import server.service.LeaderService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LeaderServiceImplTest {

    LeaderService ls = LeaderServiceImpl.getInstance();
    private static LeaderDTO leader;

    @Test
    @Order(5)
    void findByPhone() {
        String phone = "0506693793";
        LeaderDTO leaderDTO = ls.findByPhone(phone);
        assertDoesNotThrow(ResourceNotFoundException::new);
    }

    @Test
    @Order(4)
    void findByLastName() {
        String lastName = "Two";
        List<LeaderDTO> list = ls.findByLastName(lastName);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(1)
    void create() {
        leader = new LeaderDTO();
        leader.setFirstName("One");
        leader.setLastName("Two");
        leader.setFatherName("Three");
        leader.setPhone("0506693793");
        leader.setBirthdayDate(LocalDate.now());
        leader = ls.create(leader);
        System.out.println(leader);
        assertNotEquals(leader.getId(), 0);
    }

    @Test
    @Order(3)
    void update() {
        leader.setFirstName("Four");
        leader = ls.update(leader);
        System.out.println(leader);
        assertEquals(leader.getFirstName(), "Four");
    }

    @Test
    @Order(6)
    void findAll() {
        List<LeaderDTO> list = ls.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(2)
    void findById() {
        LeaderDTO leaderDTO = ls.findById(leader.getId());
        System.out.println(leaderDTO);
        assertDoesNotThrow(ResourceNotFoundException::new);
    }

    @Test
    @Order(7)
    void delete() {
        ls.delete(leader);
        List<LeaderDTO> list = ls.findAll();
        assertTrue(list.isEmpty());
    }

    @AfterAll
    static void deleteAll() {
        EntityManager em = EntityManagerClass.getInstance();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("delete l from Leader l");
        query.executeUpdate();
        em.getTransaction().commit();
    }
}
package server.service.impl;

import org.junit.jupiter.api.*;
import server.config.EntityManagerClass;
import server.dto.PositionDTO;
import server.error.ResourceNotFoundException;
import server.service.PositionService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PositionServiceImplTest {

    PositionService ps = PositionServiceImpl.getInstance();
    private static PositionDTO position;

    @Test
    @Order(6)
    void findByName() {
        String name = "New Name";
        PositionDTO positionDTO = ps.findByName(name);
        System.out.println(positionDTO);
        assertDoesNotThrow(ResourceNotFoundException::new);
    }

    @Test
    @Order(1)
    void create() {
        position = new PositionDTO();
        position.setName("Test");
        position.setSalary(112.43);
        position.setAllowance(10);
        position = ps.create(position);
        System.out.println(position.toString());
        assertNotEquals(position.getId(), 0);
    }

    @Test
    @Order(4)
    void update() {
        position.setName("New Name");
        position = ps.update(position);
        System.out.println(position.toString());
        assertEquals(position.getName(), "New Name");
    }

    @Test
    @Order(5)
    void findAll() {
        List<PositionDTO> list = ps.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(2)
    void findById() {
        int id = position.getId();
        PositionDTO positionDTO = ps.findById(id);
        System.out.println(positionDTO);
        assertDoesNotThrow(ResourceNotFoundException::new);
    }

    @Test
    @Order(3)
    void findByIdThrowException() {
        int id2 = 20;
        Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> {
            ps.findById(id2);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(7)
    void delete() {
        ps.delete(position);
        List<PositionDTO> list = ps.findAll();
        assertTrue(list.isEmpty());
    }

    @AfterAll
    static void deleteAll() {
        EntityManager em = EntityManagerClass.getInstance();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("delete p from Position p");
        query.executeUpdate();
        em.getTransaction().commit();
    }
}
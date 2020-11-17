package server.repository.impl;

import server.config.EntityManagerClass;
import server.domain.Position;
import server.repository.PositionRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PositionRepositoryImpl implements PositionRepository {

    private static PositionRepositoryImpl instance;
    private final EntityManager em = EntityManagerClass.getInstance();

    private PositionRepositoryImpl() {
    }

    public static PositionRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PositionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Position save(Position position) {
        em.getTransaction().begin();
        if (position.getId() == 0) {
            em.persist(position);
        } else {
            em.merge(position);
        }
        em.getTransaction().commit();
        return position;
    }

    @Override
    public Optional<Position> findById(Integer id) {
        Position position;
        try {
            position = em.find(Position.class, id);
        } catch (Exception e) {
            position = null;
        }
        return Optional.ofNullable(position);
    }

    @Override
    public List<Position> findAll() {
        TypedQuery<Position> query = em.createNamedQuery("findAllPositions",
                Position.class);
        return query.getResultList();
    }

    @Override
    public void delete(Position position) {
        em.getTransaction().begin();
        Position obj = em.find(Position.class, position.getId());
        Query query = em.createNativeQuery("delete from worker_position where" +
                " position_id = ?1");
        query.setParameter(1, position.getId());
        query.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(obj);
        em.getTransaction().commit();
    }

    @Override
    public Optional<Position> findByName(String name) {
        Position position;
        TypedQuery<Position> query = em.createNamedQuery("findPositionByName",
                Position.class);
        query.setParameter(1, name);
        try {
            position = query.getSingleResult();
        } catch (Exception e) {
            position = null;
        }
        return Optional.ofNullable(position);
    }

    @Override
    public List<Position> findBySalary(BigDecimal min, BigDecimal max) {
        TypedQuery<Position> query = em.createNamedQuery(
                "findPositionBySalary", Position.class);
        query.setParameter(1, min);
        query.setParameter(2, max);
        return query.getResultList();
    }
}

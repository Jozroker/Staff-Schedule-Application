package server.repository.impl;

import server.config.EntityManagerClass;
import server.domain.Leader;
import server.domain.Unit;
import server.domain.Worker;
import server.repository.UnitRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UnitRepositoryImpl implements UnitRepository {

    private static UnitRepositoryImpl instance;
    private final EntityManager em = EntityManagerClass.getInstance();

    private UnitRepositoryImpl() {
    }

    public static UnitRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UnitRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<Unit> findByName(String name) {
        Unit unit;
        TypedQuery<Unit> query = em.createNamedQuery("findUnitByName",
                Unit.class);
        query.setParameter(1, name);
        try {
            unit = query.getSingleResult();
        } catch (Exception e) {
            unit = null;
        }
        return Optional.ofNullable(unit);
    }

    @Override
    public List<Unit> findByLeader(Leader leader) {
        TypedQuery<Unit> query = em.createNamedQuery("findUnitByLeader",
                Unit.class);
        query.setParameter(1, leader);
        return query.getResultList();
    }

    @Override
    public List<Unit> findByWorker(Worker worker) {
        TypedQuery<Unit> query = em.createNamedQuery("findUnitByWorker",
                Unit.class);
        query.setParameter(1, worker);
        return query.getResultList();
    }

    @Override
    public Unit save(Unit unit) {
        em.getTransaction().begin();
        if (unit.getId() == 0) {
            em.persist(unit);
        } else {
            em.merge(unit);
        }
        em.getTransaction().commit();
        return unit;
    }

    @Override
    public Optional<Unit> findById(Integer id) {
        Unit unit;
        try {
            unit = em.find(Unit.class, id);
        } catch (Exception e) {
            unit = null;
        }
        return Optional.ofNullable(unit);
    }

    @Override
    public List<Unit> findAll() {
        TypedQuery<Unit> query = em.createNamedQuery("findAllUnits",
                Unit.class);
        return query.getResultList();
    }

    @Override
    public void delete(Unit unit) {
        em.getTransaction().begin();
        Unit obj = em.find(Unit.class, unit.getId());
        Query query = em.createNativeQuery("delete from unit_worker where " +
                "staff_schedule_test.unit_worker.unit_id = ?1");
        query.setParameter(1, unit.getId());
        query.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(obj);
        em.getTransaction().commit();
    }
}

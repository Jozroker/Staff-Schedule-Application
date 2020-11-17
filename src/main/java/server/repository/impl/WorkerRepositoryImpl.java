package server.repository.impl;

import server.config.EntityManagerClass;
import server.domain.Position;
import server.domain.Shift;
import server.domain.Unit;
import server.domain.Worker;
import server.repository.WorkerRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class WorkerRepositoryImpl implements WorkerRepository {

    private static WorkerRepositoryImpl instance;
    private final EntityManager em = EntityManagerClass.getInstance();

    private WorkerRepositoryImpl() {
    }

    public static WorkerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new WorkerRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Worker> findByLastName(String lastName) {
        TypedQuery<Worker> query = em.createNamedQuery("findWorkerByLastName",
                Worker.class);
        query.setParameter(1, lastName);
        return query.getResultList();
    }

    @Override
    public Optional<Worker> findByPhone(String phone) {
        Worker worker;
        TypedQuery<Worker> query = em.createNamedQuery("findWorkerByPhone",
                Worker.class);
        query.setParameter(1, phone);
        try {
            worker = query.getSingleResult();
        } catch (Exception e) {
            worker = null;
        }
        return Optional.ofNullable(worker);
    }

    @Override
    public List<Worker> findByShift(Shift shift) {
        TypedQuery<Worker> query = em.createNamedQuery("findWorkerByShift",
                Worker.class);
        query.setParameter(1, shift);
        return query.getResultList();
    }

    @Override
    public List<Worker> findByStage(Integer min, Integer max) {
        TypedQuery<Worker> query = em.createNamedQuery("findWorkerByStage",
                Worker.class);
        if (min == null) {
            query.setParameter(1, 1);
            query.setParameter(2, max);
        } else if (max == null) {
            query.setParameter(1, min);
            query.setParameter(2, 100);
        } else {
            query.setParameter(1, min);
            query.setParameter(2, max);
        }
        return query.getResultList();
    }

    @Override
    public List<Worker> findByUnit(Unit unit) {
        TypedQuery<Worker> query = em.createNamedQuery("findWorkerByUnit",
                Worker.class);
        query.setParameter(1, unit);
        return query.getResultList();
    }

    @Override
    public List<Worker> findByPosition(Position position) {
        TypedQuery<Worker> query = em.createNamedQuery("findWorkerByPosition",
                Worker.class);
        query.setParameter(1, position);
        return query.getResultList();
    }

    @Override
    public Worker save(Worker worker) {
        em.getTransaction().begin();
        if (worker.getId() == 0) {
            em.persist(worker);
        } else {
            em.merge(worker);
        }
        em.getTransaction().commit();
        return worker;
    }

    @Override
    public Optional<Worker> findById(Integer id) {
        Worker worker;
        try {
            worker = em.find(Worker.class, id);
        } catch (Exception e) {
            worker = null;
        }
        return Optional.ofNullable(worker);
    }

    @Override
    public List<Worker> findAll() {
        TypedQuery<Worker> query = em.createNamedQuery("findAllWorkers",
                Worker.class);
        return query.getResultList();
    }

    @Override
    public void delete(Worker worker) {
        em.getTransaction().begin();
        Worker obj = em.find(Worker.class, worker.getId());
        em.remove(obj);
        em.getTransaction().commit();
    }
}

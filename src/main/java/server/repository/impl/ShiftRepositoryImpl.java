package server.repository.impl;

import server.config.EntityManagerClass;
import server.domain.Shift;
import server.repository.ShiftRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ShiftRepositoryImpl implements ShiftRepository {

    private static ShiftRepositoryImpl instance;
    private final EntityManager em = EntityManagerClass.getInstance();

    private ShiftRepositoryImpl() {
    }

    public static ShiftRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ShiftRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Shift save(Shift shift) {
        em.getTransaction().begin();
        if (shift.getId() == 0) {
            em.persist(shift);
        } else {
            em.merge(shift);
        }
        em.getTransaction().commit();
        return shift;
    }

    @Override
    public Optional<Shift> findById(Integer id) {
        Shift shift;
        try {
            shift = em.find(Shift.class, id);
        } catch (Exception e) {
            shift = null;
        }
        return Optional.ofNullable(shift);
    }

    @Override
    public List<Shift> findAll() {
        TypedQuery<Shift> query = em.createNamedQuery("findAllShifts",
                Shift.class);
        return query.getResultList();
    }

    @Override
    public void delete(Shift shift) {
        em.getTransaction().begin();
        Shift obj = em.find(Shift.class, shift.getId());
        Query query = em.createQuery("delete from Worker w where" +
                " w.shift = ?1");
        query.setParameter(1, shift);
        query.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(obj);
        em.getTransaction().commit();
    }
}

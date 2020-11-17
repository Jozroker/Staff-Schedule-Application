package server.repository.impl;

import server.config.EntityManagerClass;
import server.domain.Leader;
import server.repository.LeaderRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaderRepositoryImpl implements LeaderRepository {

    private static LeaderRepositoryImpl instance;
    private final EntityManager em = EntityManagerClass.getInstance();

    private LeaderRepositoryImpl() {
    }

    public static LeaderRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LeaderRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<Leader> findByPhone(String phone) {
        Leader leader;
        TypedQuery<Leader> query = em.createNamedQuery("findLeaderByPhone",
                Leader.class);
        query.setParameter(1, phone);
        try {
            leader = query.getSingleResult();
        } catch (NoResultException e) {
            leader = null;
        }
        return Optional.ofNullable(leader);
    }

    @Override
    public List<Leader> findByLastName(String lastName) {
        List<Leader> leaders = new ArrayList<>();
        TypedQuery<Leader> query = em.createNamedQuery("findLeaderByLastName",
                Leader.class);
        query.setParameter(1, lastName);
        leaders = query.getResultList();
        return leaders;
    }

    @Override
    public Leader save(Leader leader) {
        em.getTransaction().begin();
        if (leader.getId() == 0) {
            em.persist(leader);
        } else {
            em.merge(leader);
        }
        em.getTransaction().commit();
        return leader;
    }

    @Override
    public Optional<Leader> findById(Integer id) {
        Leader leader;
        try {
            leader = em.find(Leader.class, id);
        } catch (Exception e) {
            leader = null;
        }
        return Optional.ofNullable(leader);
    }

    @Override
    public List<Leader> findAll() {
        TypedQuery<Leader> query = em.createNamedQuery("findAllLeaders",
                Leader.class);
        return query.getResultList();
    }

    @Override
    public void delete(Leader leader) {
        em.getTransaction().begin();
        Leader obj = em.find(Leader.class, leader.getId());
        Query query = em.createQuery("delete from Unit u where u.leader = ?1");
        query.setParameter(1, leader);
        query.executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(obj);
        em.getTransaction().commit();
    }
}

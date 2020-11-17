package server.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, E> {

    T save(T t);

    Optional<T> findById(E id);

    List<T> findAll();

    void delete(T t);
}

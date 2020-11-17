package server.service;

import server.error.ResourceNotFoundException;

import java.util.List;

public interface CrudService<T, E> {

    T create(T t);

    T update(T t);

    T findById(E id) throws ResourceNotFoundException;

    List<T> findAll();

    void delete(T t);
}

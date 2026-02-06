package com.example.charityapi.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    T update(ID id, T entity);
    void deleteById(ID id);

    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }
}

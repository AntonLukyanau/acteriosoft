package com.asteriosoft.lukyanau.testingtask.service.crud;

public interface EntityCRUDService<T> {

    boolean create(T entity);

    T findById(Long id);

    boolean update(T entity);

    boolean deleteById(Long id);

}

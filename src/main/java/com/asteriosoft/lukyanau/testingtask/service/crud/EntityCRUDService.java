package com.asteriosoft.lukyanau.testingtask.service.crud;

public interface EntityCRUDService<T> {

    void create(T entity);

    T findById(Long id);

    boolean update(T entity);

    boolean deleteById(Long id);

}

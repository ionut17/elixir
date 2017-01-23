package app.service.common;

import app.model.common.Item;

import java.util.List;

public interface BaseService<T extends Item, K> {

    List<T> findAll();

    T findById(K id);

    T add(T entity);

    T update(T user);

    void remove(K id);

    boolean entityExist(T entity);

}

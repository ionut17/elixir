package app.service;

import app.model.Item;

import java.util.List;

public interface BaseService<T extends Item> {

    public List<T> findAll();

    public T findById(long id);

    public T add(T entity);

    public T update(T user);

    public void remove(Long id);

    public boolean entityExist(T entity);

}

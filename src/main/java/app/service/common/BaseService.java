package app.service.common;

import app.model.common.Item;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BaseService<T extends Item> {

    List<T> findAll();

    T findById(long id);

    T add(T entity);

    T update(T user);

    void remove(Long id);

    boolean entityExist(T entity);

}

package app.service;

import app.model.Item;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BaseService<T extends Item> {

    List<T> findAll();

    T findById(long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    T add(T entity);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    T update(T user);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void remove(Long id);

    boolean entityExist(T entity);

}

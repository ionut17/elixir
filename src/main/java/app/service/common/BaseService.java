package app.service.common;

import app.model.common.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseService<T extends Item, K> {

    List<T> findAll();

    Page<T> findAllByPage(int page);

    Page<T> searchByPage(String query, int page);

    T findById(K id);

    T add(T entity);

    T update(T user);

    void remove(K id);

    boolean entityExist(T entity);

}

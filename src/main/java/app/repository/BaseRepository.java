package app.repository;

import app.model.common.Item;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface BaseRepository<T extends Item, K> {

    List<T> findAll();

//    T findOne(K id);

//    T save(T persisted);

//    void delete(K id);

}

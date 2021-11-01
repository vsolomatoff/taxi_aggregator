package com.solomatoff.aggregator.store;


import java.util.Collection;
import java.util.Optional;

/**
 * Data storage interface.
 * The interface is generalized
 * {@code T} - storage data type
 */
public interface Store<T> {

    /**
     * Method for saving or modifying data in the storage
     * @param model data
     * @return Optional containing saved data
     */
    Optional<T> saveOrUpdate(T model);

    /**
     * Method to find all stored data in the storage
     * @return {@code Collection<T>} stored data
     */
    Collection<T> findAll();

    /**
     * Method of data search in the storage by identifier
     * @param id identifier of data
     * @return Optional containing found data
     */
    Optional<T> findById(Integer id);

    /**
     * Data deletion method
     * @param model data
     */
    void delete(T model);

}

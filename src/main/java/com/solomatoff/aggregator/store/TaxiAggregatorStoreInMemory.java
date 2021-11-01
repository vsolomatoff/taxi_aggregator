package com.solomatoff.aggregator.store;

import com.solomatoff.aggregator.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class that implements the interface for storing data about cab aggregators registered in our service,
 * in the memory of the application.
 */
@Service
public class TaxiAggregatorStoreInMemory implements TaxiAggregatorStore {

    /**
     * Storage of cab aggregators as a collection of CopyOnWriteArrayList type
     */
    private static final Collection<TaxiAggregator> TAXI_AGGREGATORS = new CopyOnWriteArrayList<>();

    /*
     * Static block of filling the storage with initial data
     */
    static {
        TAXI_AGGREGATORS.add(
                new TaxiAggregator(0, "Агрегатор 0", "agr.yandex.ru")
        );
        TAXI_AGGREGATORS.add(
                new TaxiAggregator(1, "Агрегатор 1", "agr.google.com")
        );
        TAXI_AGGREGATORS.add(
                new TaxiAggregator(2, "Агрегатор 2", "agr.microsoft.com")
        );
        TAXI_AGGREGATORS.add(
                new TaxiAggregator(3, "Агрегатор 3", "agr.solomatoff.com")
        );
    }

    /**
     * Method for saving or modifying a cab aggregator in a storage
     * @return Optional containing the saved cab aggregator
     */
    @Override
    public Optional<TaxiAggregator> saveOrUpdate(TaxiAggregator taxiAggregator) {
        if (taxiAggregator.getName() == null || taxiAggregator.getDomain() == null) {
            return Optional.empty();
        }
        if (taxiAggregator.getId() != null) {
            Optional<TaxiAggregator> exists = findById(taxiAggregator.getId());
            if (exists.isPresent()) {
                TAXI_AGGREGATORS.remove(taxiAggregator);
            }
        }
        Optional<TaxiAggregator> max = TAXI_AGGREGATORS.stream()
                .max(Comparator.comparing(Id::getId));
        if (max.isPresent()) {
            taxiAggregator.setId(max.get().getId() + 1);
        } else {
            taxiAggregator.setId(1);
        }
        TAXI_AGGREGATORS.add(taxiAggregator);
        return Optional.of(taxiAggregator);
    }

    /**
     * Method to find all saved cab aggregators in the storage
     * @return {@code Collection<T>} of saved cab aggregators
     */
    @Override
    public Collection<TaxiAggregator> findAll() {
        return TAXI_AGGREGATORS;
    }

    /**
     * Search method of cab aggregator in the storage by identifier
     * @return Optional containing found cab aggregator
     */
    @Override
    public Optional<TaxiAggregator> findById(Integer id) {
        return TAXI_AGGREGATORS.stream().filter(taxiAggregator -> taxiAggregator.getId().equals(id)).findFirst();
    }

    /**
     * Cab Aggregator Removal Method
     * @param taxiAggregator - cab aggregator
     */
    @Override
    public void delete(TaxiAggregator taxiAggregator) {
        TAXI_AGGREGATORS.remove(taxiAggregator);
    }

}
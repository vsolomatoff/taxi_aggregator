package com.solomatoff.aggregator.store;

import com.solomatoff.aggregator.model.TaxiAggregator;
import com.solomatoff.aggregator.repository.TaxiAggregatorRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * The class that implements the interface for storing data about cab aggregators
 * registered in the service in the database.
 * Not used at the moment, because a class that implements the same interface is used for demonstration,
 * but storing data in the application's memory.
 */
//@Service
public class TaxiAggregatorStoreInDb implements TaxiAggregatorStore {

    private final TaxiAggregatorRepository taxiAggregatorRepository;

    public TaxiAggregatorStoreInDb(TaxiAggregatorRepository taxiAggregatorRepository) {
        this.taxiAggregatorRepository = taxiAggregatorRepository;
    }

    @Override
    public Collection<TaxiAggregator> findAll() {
        return (Collection<TaxiAggregator>) taxiAggregatorRepository.findAll();
    }

    @Override
    public Optional<TaxiAggregator> findById(Integer id) {
        return taxiAggregatorRepository.findById(id);
    }

    @Override
    public Optional<TaxiAggregator> saveOrUpdate(TaxiAggregator taxiAggregator) {
        return Optional.of(taxiAggregatorRepository.save(taxiAggregator));
    }

    @Override
    public void delete(TaxiAggregator taxiAggregator) {
        taxiAggregatorRepository.delete(taxiAggregator);
    }

}
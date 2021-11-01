package com.solomatoff.aggregator.repository;


import com.solomatoff.aggregator.model.TaxiAggregator;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository class for TaxiAggregator, used when storing cab aggregator data in the database
 */
public interface TaxiAggregatorRepository extends CrudRepository<TaxiAggregator, Integer> {

}
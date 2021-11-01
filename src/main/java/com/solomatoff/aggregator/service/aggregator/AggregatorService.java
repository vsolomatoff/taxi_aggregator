package com.solomatoff.aggregator.service.aggregator;

import com.solomatoff.aggregator.model.Address;
import com.solomatoff.aggregator.model.Reservation;
import com.solomatoff.aggregator.model.SearchResult;
import com.solomatoff.aggregator.model.TaxiAggregator;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface that defines the interface of the cab aggregator service.
 * Allows you to create, save, modify, delete cab aggregator objects.
 * Allows you to search for cab aggregators.
 */
public interface AggregatorService {

    /**
     * Method of saving or changing cab aggregator in the storage
     * @param taxiAggregator cab aggregator
     * @return saved cab aggregator
     */
    Optional<TaxiAggregator> saveOrUpdate(TaxiAggregator taxiAggregator);

    /**
     * Method to find all cab aggregators in the storage
     * @return {@code Collection<TaxiAggregator>} of saved cab aggregators
     */
    Collection<TaxiAggregator> findAll();

    /**
     * Method of search for cab aggregators in the storage by identifier
     * @param id identifier of cab aggregator
     * @return {@code Optional<TaxiAggregator>} of the found cab aggregator,
     * Optional can contain null if no aggregator was found.
     */
    Optional<TaxiAggregator> findById(int id);

    /**
     * Method for removing a taxi aggregator from storage
     * @param taxiAggregator cab aggregator
     */
    void delete(TaxiAggregator taxiAggregator);

    /**
     * Method to search for an offer on all cab aggregators registered in the service, for a specific address
     * @param address - address
     * @return {@code Collection<SearchResult>} of search results
     */
    Collection<SearchResult> searchByAddress(Address address);

    /**
     * Booking method for a specific offer
     * @param searchResult - the result of searching for offers on aggregators
     * @return reservation - Reservation
     */
    Reservation booking(SearchResult searchResult);

    /**
     * Cancellation method for a specific offer
     * @param reservation - reservation
     * @return true - if the cancellation was successful, and false otherwise
     */
    boolean cancelReservation(Reservation reservation);

}

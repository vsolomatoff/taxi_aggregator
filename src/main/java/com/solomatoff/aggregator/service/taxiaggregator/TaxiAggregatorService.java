package com.solomatoff.aggregator.service.taxiaggregator;

import com.solomatoff.aggregator.model.*;

import java.util.Collection;

/**
 * The interface defines the interface of the service that provides work with cab aggregators.
 * The class is generalized.
 * @param <T> - the parameter defines the type of particular cab aggregator implementation,
 * can be any type extending the basic TaxiAggregator type.
 */
public interface TaxiAggregatorService<T extends TaxiAggregator> {

    /**
     * Method to search for an offer in a specific cab aggregator for a specific address
     * @param taxiAggregator - cab aggregator
     * @param address - address
     * @return {@code Collection<SearchResult>} collection of search results
     */
    Collection<SearchResult> searchForOffersByAddress(T taxiAggregator, Address address);

    /**
     * Booking method for a specific offer
     * @param taxiAggregator - cab aggregator
     * @param offer - offer from cab aggregator
     * @return booking - Reservation
     */
    Reservation booking(T taxiAggregator, Offer offer);

    /**
     * Cancellation method for a specific offer
     * @param taxiAggregator - cab aggregator
     * @param offer - offer from cab aggregator
     * @return true - if the cancellation was successful, and false - otherwise
     */
    boolean cancelReservation(T taxiAggregator, Offer offer);
}

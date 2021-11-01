package com.solomatoff.aggregator.service.taxiaggregator;

import com.solomatoff.aggregator.model.*;
import com.solomatoff.aggregator.store.SearchResultStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * A class that implements TaxiAggregatorService interface with TaxiAggregator parameter type and uses services
 * to send requests and receive responses from(to) a cab aggregator, which imitate sending requests
 * to this aggregator and receiving responses from it.
 */
@Service
public class TaxiAggregatorServiceImpl implements TaxiAggregatorService<TaxiAggregator> {

    /**
     * Search result storage service to simulate responses from cab aggregators
     */
    private final SearchResultStore searchResultStore;

    /**
     * Constructor - creating an object with defined values
     * @param searchResultStore - object that manages the storage of results
     */
    public TaxiAggregatorServiceImpl(SearchResultStore searchResultStore) {
        this.searchResultStore = searchResultStore;
    }

    @Override
    public Collection<SearchResult> searchForOffersByAddress(TaxiAggregator taxiAggregator, Address address) {
        // Имитируем полученный ответ от агрегатора такси
        return searchResultStore.findByAddress(taxiAggregator.getId(), address);
    }

    @Override
    public Reservation booking(TaxiAggregator taxiAggregator, Offer offer) {
        // Имитируем полученный ответ от агрегатора такси
        offer.setBooking(true);
        return new Reservation(taxiAggregator.getId(), offer);
    }

    @Override
    public boolean cancelReservation(TaxiAggregator taxiAggregator, Offer offer) {
        // Имитируем полученный ответ от агрегатора такси
        offer.setBooking(false);
        return true;
    }
}

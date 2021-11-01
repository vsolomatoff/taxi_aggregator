package com.solomatoff.aggregator.service.taxiaggregator;

import com.solomatoff.aggregator.model.Address;
import com.solomatoff.aggregator.model.SearchResult;
import com.solomatoff.aggregator.model.TaxiAggregator;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * The class defines a task that runs in a thread
 * and returns {@code Collection<SearchResult>} - a collection of search results for sentences
 * for a particular address in a particular cab aggregator.
 */
public class TaxiAggregatorSearchTask implements Callable<Collection<SearchResult>> {
    /**
     * The taxiAggregatorService field is initialized through the constructor and is a service,
     * used to call methods in a particular cab aggregator.
     */
    private final TaxiAggregatorService<TaxiAggregator> taxiAggregatorService;
    /**
     * The taxiAggregator field is initialized through the constructor and is a specific aggregator
     * in which offers will be searched for at a specific address
     */
    private final TaxiAggregator taxiAggregator;
    /**
     * The address field is initialized through the constructor and is the specific address,
     * the sentences for which will be found.
     */
    private final Address address;

    /**
     * Constructor - creation of a new object with defined values
     * @param taxiAggregatorService - service used for calling methods in a particular cab aggregator.
     * @param taxiAggregator - cab aggregator
     * @param address - address for searching offers
     */
    public TaxiAggregatorSearchTask(TaxiAggregatorService<TaxiAggregator> taxiAggregatorService,
                                    TaxiAggregator taxiAggregator,
                                    Address address) {
        this.taxiAggregatorService = taxiAggregatorService;
        this.taxiAggregator = taxiAggregator;
        this.address = address;
    }

    /**
     * The method calls the search method in the service that provides work with cab aggregators.
     * @return {@code Collection<SearchResult>} - collection of search results of the suggestions
     * for a particular address in a particular cab aggregator.
     * @throws Exception Can throw Exception
     */
    @Override
    public Collection<SearchResult> call() throws Exception {
        Thread.sleep(1000);
        // возвращает в том числе и имя потока (в поле data объекта offer), который выполняет callable-таск
        Collection<SearchResult> resultCollections = taxiAggregatorService.searchForOffersByAddress(taxiAggregator, address);
        resultCollections.forEach((searchResult) -> searchResult.getOffer().setData(Thread.currentThread().getName()));
        return resultCollections;
    }

}

package com.solomatoff.aggregator.store;

import com.solomatoff.aggregator.model.Address;
import com.solomatoff.aggregator.model.SearchResult;

import java.util.Collection;

/**
 * Interface for storing search result data in cab aggregators.
 * Used to simulate returned search results from cab aggregators.
 */
public interface SearchResultStore extends Store<SearchResult> {

    Collection<SearchResult> findByAddress(int id, Address address);

}

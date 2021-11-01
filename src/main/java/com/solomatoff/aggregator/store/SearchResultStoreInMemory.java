package com.solomatoff.aggregator.store;

import com.solomatoff.aggregator.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class that implements the data storage interface for search results in cab aggregators.
 */
@Service
public class SearchResultStoreInMemory implements SearchResultStore {

    private static final Collection<SearchResult> SEARCH_RESULTS = new ArrayList<>();

    static {
        SEARCH_RESULTS.add(
                new SearchResult(0,
                        new Offer(
                                new Address(0, "Russia", "Yekaterinburg", "Lenin", "60"),
                            "New offer 0",
                                false)

                )
        );
        SEARCH_RESULTS.add(
                new SearchResult(1,
                        new Offer(
                                new Address(1, "Russia", "Yekaterinburg", "Sverdlov", "12"),
                                "New offer 1",
                                false)
                )
        );
        SEARCH_RESULTS.add(
                new SearchResult(2,
                        new Offer(
                                new Address(2, "Russia", "Moscow", "Arbat", "33"),
                                "New offer 2",
                                false)
                )
        );
        SEARCH_RESULTS.add(
                new SearchResult(3,
                        new Offer(
                                new Address(3, "Russia", "Yekaterinburg", "Lenin", "60"),
                                "New offer 3(1)",
                                false)
                )
        );
    }

    @Override
    public Collection<SearchResult> findAll() {
        return SEARCH_RESULTS;
    }

    @Override
    public Optional<SearchResult> findById(Integer id) {
        return SEARCH_RESULTS.stream()
                .filter((searchResult) -> Objects.equals(searchResult.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<SearchResult> saveOrUpdate(SearchResult searchResult) {
        if (searchResult.getId() != null) {
            Optional<SearchResult> exists = findById(searchResult.getId());
            if (exists.isPresent()) {
                SEARCH_RESULTS.remove(searchResult);
            }
        }
        Optional<SearchResult> max = SEARCH_RESULTS.stream().max(Comparator.comparing(Id::getId));
        if (max.isPresent()) {
            searchResult.setId(max.get().getId() + 1);
        } else {
            searchResult.setId(1);
        }
        SEARCH_RESULTS.add(searchResult);
        return Optional.of(searchResult);
    }

    @Override
    public void delete(SearchResult searchResult) {
        SEARCH_RESULTS.remove(searchResult);
    }

    @Override
    public Collection<SearchResult> findByAddress(int id, Address address) {
        return SEARCH_RESULTS.stream()
                .filter(
                        (searchResult) -> searchResult.getIdTaxiAggregator() == id
                            && searchResult.getOffer().getAddress().getCountry().equals(address.getCountry())
                            && searchResult.getOffer().getAddress().getCity().equals(address.getCity())
                            && searchResult.getOffer().getAddress().getStreet().equals(address.getStreet())
                            && searchResult.getOffer().getAddress().getHouse().equals(address.getHouse())
                )
                .collect(Collectors.toList());
    }
}

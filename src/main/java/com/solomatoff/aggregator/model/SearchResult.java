package com.solomatoff.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * The class that defines the structure of response data from cab aggregators
 * on request to search for offers for a specific address
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchResult extends Id {
    /**
     * Cab aggregator identifier
     */
    int idTaxiAggregator;

    /**
     * Offer from the cab aggregator whose identifier is defined in the previous field
     */
    Offer offer;
}

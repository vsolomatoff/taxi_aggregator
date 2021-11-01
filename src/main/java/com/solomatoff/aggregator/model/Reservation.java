package com.solomatoff.aggregator.model;

import lombok.*;

/**
 * The class defines the structure of reservation data
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Data
public class Reservation extends Id {

    /**
     * Cab aggregator identifier
     */
    private Integer idTaxiAggregator;

    /**
     * Offer to which the reservation is imposed
     */
    private Offer offer;

}

package com.solomatoff.aggregator.model;

import lombok.*;

/**
 * The class defines the data structure of an offer from a cab aggregator
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Data
public class Offer extends Id {

    /**
     * Address of offer
     */
    Address address;

    /**
     * Some information of this offer.
     * Currently concentrated in the form of a single string.
     * In the real project it will be different fields containing information such as:
     * cost of travel
     * car brand and license plate number
     * driver's name
     * car delivery time
     * and other data
     */
    String data;

    /**
     * Booking indication.
     * Used exclusively for tests.
     */
    Boolean booking;
}

package com.solomatoff.aggregator.model;


import lombok.*;

import javax.persistence.Entity;

/**
 * The class defines the data structure of cab aggregators that are registered in our service
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TaxiAggregator extends Id {

    public TaxiAggregator(Integer id, String name, String domain) {
        super.id = id;
        this.name = name;
        this.domain = domain;
    }

    /**
     * Cab aggregator name
     */
    String name;

    /**
     * Cab aggregator domain name
     */
    String domain;

    /*
     * Other necessary data may follow.
     * Not defined at this time because I do not know the details of the specific aggregators
     */

    @Override
    public String toString() {
        return "TaxiAggregator{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", domain='" + domain + '\''
                + '}';
    }
}

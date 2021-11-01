package com.solomatoff.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class defines the data structure for the address
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends Id {

    public Address(Integer id, String country, String city, String street, String house) {
        super.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    /**
     * Country
     */
    String country;

    /**
     * City
     */
    String city;

    /**
     * Street
     */
    String street;

    /**
     * House
     */
    String house;

}


package com.solomatoff.aggregator.service.aggregator;

import com.solomatoff.aggregator.TaxiAggregatorApplication;
import com.solomatoff.aggregator.model.Address;
import com.solomatoff.aggregator.model.Reservation;
import com.solomatoff.aggregator.model.SearchResult;
import com.solomatoff.aggregator.model.TaxiAggregator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TaxiAggregatorApplication.class)
class AggregatorServiceImplTest {

    @Autowired
    private AggregatorService aggregatorService;

    @Test
    void saveOrUpdate() {
        TaxiAggregator taxiAggregator = new TaxiAggregator(4, "Агрегатор 4", "agr.solomatoff.com");
        aggregatorService.saveOrUpdate(taxiAggregator);
        Optional<TaxiAggregator> taxiAggregator1 = aggregatorService.findById(4);
        taxiAggregator1.ifPresent(aggregator -> assertThat(aggregator, is(taxiAggregator)));
    }

    @Test
    void findAll() {
        TaxiAggregator taxiAggregator = new TaxiAggregator(4, "Агрегатор 3", "agr.solomatoff.com");
        aggregatorService.saveOrUpdate(taxiAggregator);
        Collection<TaxiAggregator> taxiAggregators = aggregatorService.findAll();
        //taxiAggregators.forEach(System.out :: println);
        assertThat(taxiAggregators.size(), is(4));
    }

    @Test
    void findById() {
        Optional<TaxiAggregator> taxiAggregator = aggregatorService.findById(0);
        taxiAggregator.ifPresent(aggregator -> assertThat(aggregator.getName(), is("Агрегатор 0")));
    }

    @Test
    void delete() {
        Optional<TaxiAggregator> taxiAggregator = aggregatorService.findById(0);
        taxiAggregator.ifPresent(aggregator -> aggregatorService.delete(aggregator));
        Collection<TaxiAggregator> taxiAggregators = aggregatorService.findAll();
        //taxiAggregators.forEach(System.out :: println);
        assertThat(taxiAggregators.size(), is(3));
    }

    @Test
    void searchByAddress() {
        Address address = new Address(1, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = aggregatorService.searchByAddress(address);
        //searchResults.forEach(System.out :: println);
        assertThat(searchResults.size(), is(2));
    }

    @Test
    void booking() {
        Address address = new Address(1, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = aggregatorService.searchByAddress(address);
        for (SearchResult searchResult : searchResults) {
            Reservation reservation = aggregatorService.booking(searchResult);
            System.out.println("reservation = " + reservation);
            assertThat(reservation.getOffer().getAddress().getCountry(), is("Russia"));
            assertThat(reservation.getOffer().getAddress().getCity(), is("Yekaterinburg"));
            assertThat(reservation.getOffer().getAddress().getStreet(), is("Lenin"));
            assertThat(reservation.getOffer().getAddress().getHouse(), is("60"));
        }
    }

    @Test
    void cancelReservation() {
        Address address = new Address(1, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = aggregatorService.searchByAddress(address);
        for (SearchResult searchResult : searchResults) {
            Reservation reservation = aggregatorService.booking(searchResult);
            System.out.println("reservation before cancel = " + reservation);
            aggregatorService.cancelReservation(reservation);
            System.out.println("reservation after cancel = " + reservation);
            assertThat(reservation.getOffer().getBooking(), is(false));
        }
    }
}
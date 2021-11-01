package com.solomatoff.aggregator.controller;

import com.solomatoff.aggregator.exception.IllegalRequestBodyException;
import com.solomatoff.aggregator.model.Address;
import com.solomatoff.aggregator.model.Reservation;
import com.solomatoff.aggregator.model.SearchResult;
import com.solomatoff.aggregator.model.TaxiAggregator;
import com.solomatoff.aggregator.service.aggregator.AggregatorService;
import com.solomatoff.aggregator.validation.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Rest-controller class for processing service requests
 */
@RestController
@RequestMapping("/api/v1")
public class V1AggregatorController {

    private final AggregatorService aggregatorService;

    public V1AggregatorController(final AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/aggregators")
    public List<TaxiAggregator> findAll() {
        return new ArrayList<>(aggregatorService.findAll());
    }

    @PostMapping("/registration")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<TaxiAggregator> newAggregator(
            @Valid @RequestBody TaxiAggregator taxiAggregatorService
    ) {
        Optional<TaxiAggregator> aggregator = this.aggregatorService.saveOrUpdate(taxiAggregatorService);
        if (aggregator.isEmpty()) {
            throw new IllegalRequestBodyException("Aggregator is illegal");
        }
        return new ResponseEntity<>(
                aggregator.get(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/search")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Collection<SearchResult>> search(
            @Valid @RequestBody Address address
    ) {
        Collection<SearchResult> searchResults = this.aggregatorService.searchByAddress(address);
        return new ResponseEntity<>(
                searchResults,
                HttpStatus.OK
        );
    }

    @PutMapping("/booking")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Reservation> booking(
            @Valid @RequestBody SearchResult searchResult
    ) {
        Reservation reservation = this.aggregatorService.booking(searchResult);
        if (reservation == null) {
            throw new IllegalRequestBodyException("SearchResult is illegal");
        }
        return new ResponseEntity<>(
                reservation,
                HttpStatus.OK
        );
    }

    @PutMapping("/cancel")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Boolean> cancelBooking(
            @Valid @RequestBody Reservation reservation
    ) {
        boolean cancelResult = this.aggregatorService.cancelReservation(reservation);
        if (!cancelResult) {
            throw new IllegalRequestBodyException("Reservation is illegal");
        }
        return new ResponseEntity<>(
                true,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/aggregator/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var optionalAggregator = aggregatorService.findById(id);
        if (optionalAggregator.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aggregator is not found. Please, check ID."
            );
        }
        TaxiAggregator aggregatorService = optionalAggregator.get();
        this.aggregatorService.delete(aggregatorService);
        return ResponseEntity.ok().build();
    }

}
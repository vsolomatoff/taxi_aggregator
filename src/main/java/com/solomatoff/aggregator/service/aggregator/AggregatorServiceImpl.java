package com.solomatoff.aggregator.service.aggregator;

import com.solomatoff.aggregator.model.Address;
import com.solomatoff.aggregator.model.Reservation;
import com.solomatoff.aggregator.model.SearchResult;
import com.solomatoff.aggregator.model.TaxiAggregator;
import com.solomatoff.aggregator.service.taxiaggregator.TaxiAggregatorService;
import com.solomatoff.aggregator.service.taxiaggregator.TaxiAggregatorSearchTask;
import com.solomatoff.aggregator.store.TaxiAggregatorStoreInMemory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

/**
 * Cab aggregator aggregator service class.
 * Allows you to create, save, modify, delete cab aggregator objects.
 * Allows you to search for cab aggregators.
 */
@Service
public class AggregatorServiceImpl implements AggregatorService {

    /**
     * The storage object responsible for storing cab aggregators in the application's memory.
     */
    private final TaxiAggregatorStoreInMemory taxiAggregatorStoreInMemory;

    /**
     * Service used to call methods in a particular cab aggregator
     */
    private final TaxiAggregatorService<TaxiAggregator> taxiAggregatorService;

    /**
     * Constructor - creating a new object with certain values
     * @param taxiAggregatorStoreInMemory - storage object responsible for storing cab aggregators
     *                                    in the application's memory.
     * @param taxiAggregatorService - service used for calling methods in a particular cab aggregator.
     */
    public AggregatorServiceImpl(TaxiAggregatorStoreInMemory taxiAggregatorStoreInMemory,
                                 TaxiAggregatorService<TaxiAggregator> taxiAggregatorService) {
        this.taxiAggregatorStoreInMemory = taxiAggregatorStoreInMemory;
        this.taxiAggregatorService = taxiAggregatorService;
    }

    /**
     * Method of saving or changing cab aggregator in the storage
     * @param taxiAggregator cab aggregator
     * @return saved cab aggregator
     */
    @Override
    public Optional<TaxiAggregator> saveOrUpdate(TaxiAggregator taxiAggregator) {
        return taxiAggregatorStoreInMemory.saveOrUpdate(taxiAggregator);
    }

    /**
     * Method to find all cab aggregators in the storage
     * @return collection {@code Collection<TaxiAggregator>} of saved cab aggregators
     */
    @Override
    public Collection<TaxiAggregator> findAll() {
        return taxiAggregatorStoreInMemory.findAll();
    }

    /**
     * Method of search for cab aggregators in the storage by identifier
     * @return {@code Optional<TaxiAggregator>} of the found cab aggregator,
     * Optional can contain null if no aggregator was found.
     */
    @Override
    public Optional<TaxiAggregator> findById(int id) {
        return taxiAggregatorStoreInMemory.findById(id);
    }

    /**
     * Method for removing a taxi aggregator from storage
     */
    @Override
    public void delete(TaxiAggregator taxiAggregator) {
        taxiAggregatorStoreInMemory.delete(taxiAggregator);
    }

    /**
     * Method to search for an offer on all cab aggregators registered in the service, for a specific address
     * @param address - address
     * @return collection {@code Collection<SearchResult>} of search results
     */
    @Override
    public Collection<SearchResult> searchByAddress(Address address) {

        //Получаем ExecutorService утилитного класса Executors с размером пула потоков равному 10
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //создаем список с Future, которые ассоциированы с Callable
        Collection<Future<Collection<SearchResult>>> list = new ArrayList<>();

        for (TaxiAggregator taxiAggregator : findAll()) {
            // создаем экземпляр AggregatorTask
            Callable<Collection<SearchResult>> callable = new TaxiAggregatorSearchTask(
                    taxiAggregatorService,
                    taxiAggregator,
                    address
            );
            //"сабмитим" Callable-таски, которые будут выполнены пулом потоков
            Future<Collection<SearchResult>> future = executor.submit(callable);
            //добавляя Future в список, мы сможем получить результат выполнения
            list.add(future);
        }
        Collection<SearchResult> listResult = new ArrayList<>();
        for (Future<Collection<SearchResult>> fut : list) {
            try {
                listResult.addAll(fut.get(5000, TimeUnit.MILLISECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return listResult;
    }

    /**
     * Booking method for a specific offer
     * @param searchResult - the result of searching for offers on aggregators
     * @return reservation - Reservation
     */
    @Override
    public Reservation booking(SearchResult searchResult) {
        Optional<TaxiAggregator> optionalTaxiAggregator = findById(searchResult.getIdTaxiAggregator());
        Reservation reservation = null;
        if (optionalTaxiAggregator.isPresent()) {
            TaxiAggregator taxiAggregator = optionalTaxiAggregator.get();
            reservation = taxiAggregatorService.booking(taxiAggregator, searchResult.getOffer());
        }
        return reservation;
    }

    /**
     * Cancellation method for a specific offer
     * @param reservation - reservation
     * @return true - if the cancellation was successful, and false otherwise
     */
    @Override
    public boolean cancelReservation(Reservation reservation) {
        Optional<TaxiAggregator> optionalTaxiAggregator = findById(reservation.getIdTaxiAggregator());
        return optionalTaxiAggregator
                .filter(
                        taxiAggregator -> taxiAggregatorService.cancelReservation(
                                taxiAggregator,
                                reservation.getOffer())
                )
                .isPresent();
    }
}

package com.solomatoff.aggregator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solomatoff.aggregator.TaxiAggregatorApplication;
import com.solomatoff.aggregator.model.*;
import com.solomatoff.aggregator.service.aggregator.AggregatorService;
import com.solomatoff.aggregator.store.SearchResultStore;
import com.solomatoff.aggregator.store.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TaxiAggregatorApplication.class)
@AutoConfigureMockMvc
class V1AggregatorControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    private AggregatorService aggregatorService;

    @Autowired
    private SearchResultStore searchResultStore;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenRegistrationSuccess() throws Exception {
        TaxiAggregator taxiAggregator = new TaxiAggregator(5, "Aggregator 5", "agr.site.com");
        String reqJson = MAPPER.writeValueAsString(taxiAggregator);
        when(aggregatorService.saveOrUpdate(taxiAggregator)).thenReturn(Optional.of(taxiAggregator));
        mockMvc.perform(post(
                        "/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void whenRegistrationNotSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/registration").contentType(MediaType.APPLICATION_JSON)
                        .content("Aggregator 5"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void whenSearchSuccess() throws Exception {
        Address address = new Address(null, "Russia", "Yekaterinburg", "Lenin", "60");
        String reqJson = MAPPER.writeValueAsString(address);
        Collection<SearchResult> searchResults = searchResultStore.findByAddress(0, address);
        searchResults.addAll(searchResultStore.findByAddress(3, address));
        //searchResults.forEach(System.out ::println);

        String respJson = new ObjectMapper().writeValueAsString(searchResults);
        when(aggregatorService.searchByAddress(address)).thenReturn(searchResults);

        mockMvc.perform(get(
                        "/api/v1/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                )
                .andDo(print())
                .andExpect(content().json(respJson))
                .andExpect(status().isOk());

    }

    @Test
    public void whenSearchEmpty() throws Exception {
        Address address = new Address(null, "Russia", "Moscow", "Lenin", "60");
        String reqJson = MAPPER.writeValueAsString(address);
        Collection<SearchResult> searchResults = new ArrayList<>();

        String respJson = new ObjectMapper().writeValueAsString(searchResults);
        when(aggregatorService.searchByAddress(address)).thenReturn(searchResults);

        mockMvc.perform(get(
                        "/api/v1/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson)
                )
                .andDo(print())
                .andExpect(content().json(respJson))
                .andExpect(status().isOk());

    }

    @Test
    public void whenBookingSuccess() throws Exception {
        Address address = new Address(null, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = searchResultStore.findByAddress(0, address);
        searchResults.addAll(searchResultStore.findByAddress(3, address));
        //searchResults.forEach(System.out ::println);

        for (SearchResult searchResult : searchResults) {
            String reqJson = MAPPER.writeValueAsString(searchResult);
            Reservation reservation = new Reservation(searchResult.getIdTaxiAggregator(), searchResult.getOffer());
            when(aggregatorService.booking(searchResult)).thenReturn(reservation);
            String respJson = new ObjectMapper().writeValueAsString(reservation);
            mockMvc.perform(put(
                            "/api/v1/booking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson)
                    )
                    .andDo(print())
                    .andExpect(content().json(respJson))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void whenBookingUnsuccessful() throws Exception {
        Address address = new Address(null, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = searchResultStore.findByAddress(0, address);
        searchResults.addAll(searchResultStore.findByAddress(3, address));
        //searchResults.forEach(System.out ::println);

        for (SearchResult searchResult : searchResults) {
            String reqJson = MAPPER.writeValueAsString(searchResult);
            when(aggregatorService.booking(searchResult)).thenReturn(null);
            mockMvc.perform(put(
                            "/api/v1/booking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void whenCancelBookingSuccess() throws Exception {
        Address address = new Address(null, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = searchResultStore.findByAddress(0, address);
        searchResults.addAll(searchResultStore.findByAddress(3, address));
        //searchResults.forEach(System.out ::println);
        for (SearchResult searchResult : searchResults) {
            Reservation reservation = new Reservation(searchResult.getIdTaxiAggregator(), searchResult.getOffer());
            String reqJson = MAPPER.writeValueAsString(reservation);
            when(aggregatorService.cancelReservation(reservation)).thenReturn(true);
            mockMvc.perform(put(
                            "/api/v1/cancel")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson)
                    )
                    .andDo(print())
                    .andExpect(content().string("true"))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void whenCancelBookingUnsuccessful() throws Exception {
        Address address = new Address(null, "Russia", "Yekaterinburg", "Lenin", "60");
        Collection<SearchResult> searchResults = searchResultStore.findByAddress(0, address);
        searchResults.addAll(searchResultStore.findByAddress(3, address));
        //searchResults.forEach(System.out ::println);
        for (SearchResult searchResult : searchResults) {
            Reservation reservation = new Reservation(searchResult.getIdTaxiAggregator(), searchResult.getOffer());
            String reqJson = MAPPER.writeValueAsString(reservation);
            when(aggregatorService.cancelReservation(reservation)).thenReturn(false);
            mockMvc.perform(put(
                            "/api/v1/cancel")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reqJson)
                    )
                    .andDo(print())
                    .andExpect(content().string("{\"details\":\"Reservation is illegal\",\"message\":\"Some of fields is illegal\"}"))
                    .andExpect(status().isBadRequest());
        }
    }

}
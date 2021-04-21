package com.punko.rest.service;


import com.punko.ResidentService;
import com.punko.model.Apartment;
import com.punko.model.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ResidentServiceRest implements ResidentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ResidentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Resident> findAll() {
        LOGGER.debug("find all residents() ");
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Resident>>() {
        }).getBody();
    }

    @Override
    public void create(Resident resident) {
        LOGGER.debug("create resident ({})", resident);
        restTemplate.postForEntity(url, resident, Resident.class).getBody();
    }

    //    @Override
//    public List<Apartment> getAllApartmentNumber() {
//        LOGGER.debug("find all apartment numbers() ");
//        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
//        return (List<Apartment>) responseEntity.getBody();
//    }
    @Override
    public List<Apartment> getAllApartmentNumber() {
        LOGGER.debug("find all apartment numbers() ");
        List<Apartment> apartmentsNumber = restTemplate.getForObject(url, List.class);
        return apartmentsNumber;
    }

    @Override
    public Resident findById(Integer id) {
        LOGGER.debug("find resident by id({})", id);
        ResponseEntity<Resident> responseEntity =
                restTemplate.getForEntity(url + "/" + id, Resident.class);
        return responseEntity.getBody();
    }

    @Override
    public void update(Resident resident) {
        LOGGER.debug("update resident({})", resident);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Resident> entity = new HttpEntity<>(resident, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Resident.class).getBody();
    }

    @Override
    public Integer delete(Integer id) {
        LOGGER.debug("delete resident({})", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Apartment> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

//    @Override
//    public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) {
//        String searchUrl = "/search?arrivalTime={arrivalTime}&departureTime={departureTime}";
//        ResponseEntity<List<Resident>> responseSearch = restTemplate.exchange(
//                url + searchUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                },
//                arrivalTime, departureTime);
//        return responseSearch.getBody();
//    }

    @Override
    public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) {
        LOGGER.debug("searchByTwoDates() {} {}", arrivalTime, departureTime);
        String arrivalTimeString = arrivalTime == null ? "" : arrivalTime.toString();
        String departureTimeString = departureTime == null ? "" : departureTime.toString();


        String searchUrl = new StringBuilder(url)
                .append("/search?arrivalTime=").append(arrivalTimeString)
                .append("&departureTime=").append(departureTimeString)
                .toString();
        return restTemplate.exchange(searchUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Resident>>() {
        }).getBody();
    }

    @Override
    public Integer count() {
        return null;
    }

}
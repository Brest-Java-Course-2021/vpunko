package com.punko;


import com.punko.model.Apartment;
import com.punko.model.Resident;
import com.punko.model.ResidentSearchByDate.ResidentSearchByDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ResidentServiceRest implements ResidentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentServiceRest.class);

    private final String url;

    private RestTemplate restTemplate;

    public ResidentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Resident> findAll() {
        LOGGER.debug("find all residents() ");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Resident>) responseEntity.getBody();
    }

    @Override
    public void create(Resident resident) {
        LOGGER.debug("create resident ({})", resident);
        restTemplate.postForEntity(url, resident, Resident.class);
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
//        restTemplate.put(url, department);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Resident> entity = new HttpEntity<>(resident, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Resident.class);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("delete resident({})", id);
//        restTemplate.delete(url + "/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Resident> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, Resident.class);
    }

    @Override
    public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) {
        ResponseEntity<List<Resident>> responseSearch = restTemplate.exchange(
                url + "/search?arrivalTime={arrivalTime}&departureTime={departureTime}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                arrivalTime, departureTime);
        return responseSearch.getBody();
    }
}

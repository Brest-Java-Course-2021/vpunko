package com.punko;

import com.punko.model.Apartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ApartmentServiceRest implements ApartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ApartmentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Apartment> findAll() {
        LOGGER.debug("find all apartment() ");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Apartment>) responseEntity.getBody();
    }

    @Override
    public Apartment findById(Integer apartmentId) {
        LOGGER.debug("find apartment by id({})", apartmentId);
        ResponseEntity<Apartment> responseEntity =
                restTemplate.getForEntity(url + "/" + apartmentId, Apartment.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(Apartment apartment) {
        LOGGER.debug("create apartment ({})", apartment);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, apartment, Integer.class);
        return (Integer) responseEntity.getBody();

    }

    @Override
    public Integer update(Apartment apartment) {
        LOGGER.debug("update apartment({})", apartment);
//        restTemplate.put(url, department);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Apartment> entity = new HttpEntity<>(apartment, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer apartmentId) {
        LOGGER.debug("delete apartment({})", apartmentId);
//        restTemplate.delete(url + "/" + departmentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Apartment> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + apartmentId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public List<String> getAllApartmentClass() {
        return null;
    }
}

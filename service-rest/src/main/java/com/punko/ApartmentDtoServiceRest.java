package com.punko;

import com.punko.model.dto.ApartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class ApartmentDtoServiceRest implements ApartmentDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDtoServiceRest.class);

    private final String url;

    private final RestTemplate restTemplate;

    public ApartmentDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ApartmentDto> findAllWithAvgTime() {
        LOGGER.debug("findAllWithAvgSalary()");
        return restTemplate.exchange(url, GET, null,
                new ParameterizedTypeReference<List<ApartmentDto>>() {}).getBody();
    }
}

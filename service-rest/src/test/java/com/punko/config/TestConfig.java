package com.punko.config;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.ResidentService;
import com.punko.rest.service.ApartmentDtoServiceRest;
import com.punko.rest.service.ApartmentServiceRest;
import com.punko.rest.service.ResidentServiceRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    public static final String APARTMENT_DTO_URL = "http://localhost:8090/apartments/dto";
    public static final String APARTMENT_URL = "http://localhost:8090/apartments";
    public static final String RESIDENT_URL = "http://localhost:8090/residents";

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    ApartmentDtoService apartmentDtoService() {
        return new ApartmentDtoServiceRest(APARTMENT_DTO_URL, restTemplate());
    }

    @Bean
    ApartmentService apartmentService() {
        return new ApartmentServiceRest(APARTMENT_URL, restTemplate());
    }

    @Bean
    ResidentService residentService() {
        return new ResidentServiceRest(RESIDENT_URL, restTemplate());
    }

}

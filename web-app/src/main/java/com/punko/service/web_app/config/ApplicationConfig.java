package com.punko.service.web_app.config;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.ResidentService;
import com.punko.rest.service.ApartmentDtoServiceRest;
import com.punko.rest.service.ApartmentServiceRest;
import com.punko.rest.service.ResidentServiceRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan({"com.punko.service", "com.punko.dao"})
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    ApartmentDtoService apartmentDtoService() {
        String url = String.format("%s://%s:%d/apartments/dto", protocol, host, port);
        return new ApartmentDtoServiceRest(url, restTemplate());
    }

    @Bean
    ApartmentService apartmentService() {
        String url = String.format("%s://%s:%d/apartments", protocol, host, port);
        return new ApartmentServiceRest(url, restTemplate());
    }

    @Bean
    ResidentService residentService() {
        String url = String.format("%s://%s:%d/residents", protocol, host, port);
        return new ResidentServiceRest(url, restTemplate());
    }
}

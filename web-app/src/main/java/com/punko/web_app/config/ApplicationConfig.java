package com.punko.web_app.config;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.dao.ResidentDao;
import com.punko.dao.jdbc.ResidentDaoJdbc;
import com.punko.rest.service.ApartmentDtoServiceRest;
import com.punko.rest.service.ApartmentServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.punko.dao.jdbc", "com.punko"})
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
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

    @Autowired
    DataSource dataSource;

    @Bean
    ResidentDao residentDao() {
        return new ResidentDaoJdbc(dataSource);
    }

}

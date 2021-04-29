package com.punko.config;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.ResidentService;
import com.punko.dao.ResidentDao;
import com.punko.dao.jdbc.ResidentDaoJdbc;
import com.punko.rest.service.ApartmentDtoServiceRest;
import com.punko.rest.service.ApartmentServiceRest;
import com.punko.rest.service.ResidentServiceRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

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

//    @Bean
//    ResidentService residentService() {
//        return new ResidentServiceRest(RESIDENT_URL, restTemplate());
//    }

    @Bean
    ResidentService residentService() {
        return new ResidentServiceRest(restTemplate());
    }

    @Bean
    ResidentDao residentDao() {
        return new ResidentDaoJdbc(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create-test-db.sql")
                .addScript("init-test-db.sql")
                .build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}

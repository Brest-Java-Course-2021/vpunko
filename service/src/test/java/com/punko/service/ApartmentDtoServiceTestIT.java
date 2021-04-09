package com.punko.service;

import com.punko.ApartmentDtoService;
import com.punko.dao.jdbc.ApartmentDtoDaoJdbc;
import com.punko.model.dto.ApartmentDto;
import com.punko.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Import({ApartmentDtoDaoJdbc.class, ApartmentDtoServiceImpl.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@PropertySource({"classpath:dao.properties"})
@ComponentScan(basePackages = {"com.punko.dao", "com.punko.testdb"})
@Transactional
public class ApartmentDtoServiceTestIT {

    @Autowired
    ApartmentDtoService apartmentDtoService;

    @Test
    public void findAllWithAvgTimeTest() {
        List<ApartmentDto> apartmentDtoList = apartmentDtoService.findAllWithAvgTime();
        Assertions.assertNotNull(apartmentDtoList);
        Assertions.assertTrue(apartmentDtoList.size() > 0);
    }
}





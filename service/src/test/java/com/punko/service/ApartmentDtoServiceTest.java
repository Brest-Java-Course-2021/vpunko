package com.punko.service;

import com.punko.ApartmentDtoService;
import com.punko.model.dto.ApartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class ApartmentDtoServiceTest {

    @Autowired
    ApartmentDtoService apartmentDtoService;

    @Test
    public void findAllWithAvgTimeTest() {
        List<ApartmentDto> apartmentDtoList = apartmentDtoService.findAllWithAvgTime();
        Assertions.assertNotNull(apartmentDtoList);
        Assertions.assertTrue(apartmentDtoList.size() > 0);


    }
}





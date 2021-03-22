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

        LocalDate future = LocalDate.of(2100, 1, 1);
        LocalDate past = LocalDate.of(2000, 1, 1);
//        Assertions.assertTrue(apartmentDtoList.get(1).getAvgTime().intValue() > 0);
//        Assertions.assertTrue(apartmentDtoList.get(0).getAvgTime().isAfter(past));
        Assertions.assertTrue(apartmentDtoList.get(0).getMaxDepartureTime().isAfter(past));

    }

}





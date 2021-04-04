package com.punko.service;

import com.punko.dao.ResidentDao;
import com.punko.model.Resident;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class ResidentServiceTestIT {

    @Autowired
    ResidentDao residentDao;

    @Test
    public void getAllResidentTest() {
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

    @Test
    public void createResidentTest() {
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        LocalDate arrivalTime = LocalDate.of(2020, 03, 20);
        LocalDate departureTime = LocalDate.of(2021, 05, 21);
//        Date arrivalTime = Date.valueOf("2020-02-12");
//        Date departureTime = Date.valueOf("2020-04-25");
        Resident resident = new Resident("Name", "Surname", "test@test", arrivalTime, departureTime, 102);
        residentDao.create(resident);

        List<Resident> modifiedList = residentDao.findAll();
        Assertions.assertNotNull(modifiedList);
        Assertions.assertTrue(modifiedList.size() > 0);
        Assertions.assertEquals(residentList.size() + 1, modifiedList.size());
    }

}

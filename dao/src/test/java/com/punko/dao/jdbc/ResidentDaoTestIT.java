package com.punko.dao.jdbc;

import com.punko.dao.ResidentDao;
import com.punko.model.Apartment;
import com.punko.model.Resident;
import com.punko.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

@DataJdbcTest
@Import({ResidentDaoJdbc.class})
@PropertySource({"classpath:daoResident.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResidentDaoTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentDaoTestIT.class);

    @Autowired
    ResidentDao residentDao;

    @Test
    public void getAllResidentTest() {
        LOGGER.debug("should find all residents()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

    @Test
    public void findByIdResidentTest() {
        LOGGER.debug("should find resident by id()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        int id = residentList.get(0).getResidentId();
        Resident resident = residentDao.findById(id);

        Assertions.assertNotNull(resident);
        Assertions.assertEquals(residentList.get(0), resident);
    }

    @Test
    public void findByIdExceptionTest() {
        LOGGER.debug("should find exception by id()");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            residentDao.findById(999);
        });
    }

    @Test
    public void createResidentTest() {
        LOGGER.debug("should create resident()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        LocalDate arrivalTime = LocalDate.of(2020, 03, 20);
        LocalDate departureTime = LocalDate.of(2021, 05, 21);
        Resident resident = new Resident("Name", "Surname", "test@test", arrivalTime, departureTime, 102);
        residentDao.create(resident);

        List<Resident> modifiedList = residentDao.findAll();
        Assertions.assertNotNull(modifiedList);
        Assertions.assertTrue(modifiedList.size() > 0);
        Assertions.assertEquals(residentList.size() + 1, modifiedList.size());
    }

    @Test
    public void isResidentEmailUniqueTest() {
        LOGGER.debug("create resident with same email test()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        LocalDate arrivalTime = LocalDate.of(2020, 03, 20);
        LocalDate departureTime = LocalDate.of(2021, 05, 21);
        Resident resident = new Resident("Name", "Surname", residentList.get(0).getEmail(), arrivalTime, departureTime, 102);
        Assertions.assertThrows(IllegalArgumentException.class, () -> residentDao.create(resident));
    }

    @Test
    public void getAllApartmentNumberTest() {
        LOGGER.debug("should return all apartment numbers ()");
        List<Apartment> residentList = residentDao.getAllApartmentNumber();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

    @Test
    public void updateResidentTest() {
        LOGGER.debug("should update resident()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);

        Resident resident = residentList.get(0);
        resident.setFirstName("testName");

        residentDao.updateResident(resident);
        Resident realResident = residentDao.findById(resident.getResidentId());
        Assertions.assertEquals("testName", realResident.getFirstName());
        Assertions.assertEquals(resident, realResident);
    }

    @Test
    public void updateApartmentWithTheSameNumberTest() {
        LOGGER.debug("Update resident with the same email");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);

        Resident resident = residentList.get(0);
        resident.setEmail(residentList.get(1).getEmail());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            residentDao.updateResident(resident);
        });
    }

    @Test
    public void deleteResidentTest() {
        LOGGER.debug("should delete resident()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        residentDao.delete(residentList.get(0).getResidentId());
        List<Resident> afterDeleteList = residentDao.findAll();

        Assertions.assertEquals(residentList.size(), afterDeleteList.size() + 1);
    }

    @Test
    public void getAllResidentByTimeTest() {
        LOGGER.debug("should return Resident from arrivalTime to departureTime()");
        LocalDate arrivalTime = LocalDate.of(2021, 01, 20);
        LocalDate departureTime = LocalDate.of(2022, 05, 21);

        List<Resident> residentList = residentDao.findAllByTime(arrivalTime, departureTime);
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

    @Test
    public void countResidentTest() {
        LOGGER.debug("should return count of Resident()");
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        int count = residentDao.count();
        Assertions.assertEquals(residentList.size(), count);
    }

}

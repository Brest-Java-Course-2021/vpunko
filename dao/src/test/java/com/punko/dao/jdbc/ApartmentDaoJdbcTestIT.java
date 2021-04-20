package com.punko.dao.jdbc;

import com.punko.dao.ApartmentDao;
import com.punko.model.Apartment;
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

import java.util.List;

import static com.punko.model.constants.ApartmentClassConst.MEDIUM;


@DataJdbcTest
@Import({ApartmentDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApartmentDaoJdbcTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDaoJdbcTestIT.class);

    @Autowired
    private ApartmentDao apartmentDao;

    @Test
    public void findAllTest() {
        LOGGER.debug("should find all apartments()");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);
    }

    @Test
    public void findByIdTest() {
        LOGGER.debug("should find apartment by id()");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);

        Integer apartmentId = apartmentList.get(0).getApartmentId();
        Apartment apartment = apartmentDao.findById(apartmentId);
        Assertions.assertNotNull(apartment);
        Assertions.assertEquals(apartment.getApartmentNumber(), apartmentList.get(0).getApartmentNumber());
    }

    @Test
    public void findByIdExceptionTest() {
        LOGGER.debug("should find exception by id()");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            apartmentDao.findById(999);
        });
    }

    @Test
    public void createTest() {
        LOGGER.debug("should create apartment()");
        Integer countBeforeCreate = apartmentDao.count();
        apartmentDao.create(new Apartment(110, MEDIUM));
        Integer countAfterCreate = apartmentDao.count();
        Assertions.assertEquals(countBeforeCreate + 1, countAfterCreate);
    }

    @Test
    public void createWithTheSameNumberTest() {
        LOGGER.debug("create apartment with same number test()");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            apartmentDao.create(new Apartment(10, MEDIUM));
            apartmentDao.create(new Apartment(10, MEDIUM));
        });
    }

    @Test
    public void updateApartmentTest() {
        LOGGER.debug("should update apartment()");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentNumber(200);

        apartmentDao.update(apartment);
        Apartment realApartment = apartmentDao.findById(apartment.getApartmentId());
        Assertions.assertEquals(200, realApartment.getApartmentNumber());
        Assertions.assertEquals(apartment, realApartment);
    }

    @Test
    public void updateApartmentWithTheSameNumberButDiffClassTest() {
        LOGGER.debug("Update apartment with the same number but different class test");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentClass(apartmentList.get(1).getApartmentClass());
        Assertions.assertEquals(apartment.getApartmentClass(), apartmentList.get(1).getApartmentClass());
    }

    @Test
    public void updateApartmentWithTheSameNumberTest() {
        LOGGER.debug("Update apartment with the same number");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentNumber(apartmentList.get(1).getApartmentNumber());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            apartmentDao.update(apartment);
        });
    }

    @Test
    public void deleteApartmentTest() {
        LOGGER.debug("should delete Apartment()");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        Integer countBeforeDelete = apartmentDao.count();
        apartmentDao.delete(apartmentList.get(0).getApartmentId());
        Integer countAfterDelete = apartmentDao.count();
        Assertions.assertEquals(countBeforeDelete, countAfterDelete + 1);
    }

    @Test
    public void countApartmentTest() {
        LOGGER.debug("should return count of Apartment()");
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        int count = apartmentDao.count();
        Assertions.assertEquals(apartmentList.size(), count);
    }

}
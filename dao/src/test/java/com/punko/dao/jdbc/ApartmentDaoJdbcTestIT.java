package com.punko.dao.jdbc;

import com.punko.dao.ApartmentDao;
import com.punko.model.Apartment;
import com.punko.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.punko.model.constants.ApartmentClassConst.*;


@DataJdbcTest
@Import({ApartmentDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApartmentDaoJdbcTestIT {

    @Autowired
    private ApartmentDao apartmentDao;

    @Test
    public void findAllTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);
    }

    @Test
    public void findByIdTest() {
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
        Assertions.assertNull(apartmentDao.findById(999));
    }

    @Test
    public void createTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);

        apartmentDao.create(new Apartment(110, MEDIUM));

        List<Apartment> realApartment = apartmentDao.findAll();
        Assertions.assertEquals(realApartment.size(), apartmentList.size()+1);
        realApartment.stream().forEach(System.out::println);
    }

    @Test
    public void createWithTheSameNumberTest() {
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
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentClass(apartmentList.get(1).getApartmentClass());
        Assertions.assertEquals(apartment.getApartmentClass(), apartmentList.get(1).getApartmentClass());
    }

    @Test
    public void updateApartmentWithTheSameNumberTest() {
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
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartmentDao.delete(apartment.getApartmentId());
        List<Apartment> realApartment = apartmentDao.findAll();

        Assertions.assertEquals(realApartment.size() + 1, apartmentList.size());
    }

    @Test
    public void countApartmentTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        int count = apartmentDao.count();
        Assertions.assertEquals(apartmentList.size(), count);
    }

}
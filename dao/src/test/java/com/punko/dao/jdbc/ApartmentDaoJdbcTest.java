package com.punko.dao.jdbc;

import com.punko.dao.ApartmentDao;
import com.punko.model.Apartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
class ApartmentDaoJdbcTest {

    @Autowired
    private ApartmentDao apartmentDao;

    @Test
    public void findAllTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        assertTrue(apartmentList.size() > 0);
        apartmentList.stream().forEach(System.out::println);
    }

    @Test
    public void findByIdTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        assertTrue(apartmentList.size() > 0);

        Integer apartmentId = apartmentList.get(0).getApartmentId();
        Apartment apartment = apartmentDao.findById(apartmentId);
        assertNotNull(apartment);
        assertEquals(apartment.getApartmentNumber(), apartmentList.get(0).getApartmentNumber());
    }

    @Test
    public void findByIdExceptionTest() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> { apartmentDao.findById(999); } );
    }

    @Test
    public void createTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        assertTrue(apartmentList.size() > 0);

        apartmentDao.create(new Apartment(110, "MEDIUM"));

        List<Apartment> realApartment = apartmentDao.findAll();
        assertEquals(realApartment.size(), apartmentList.size()+1);
        realApartment.stream().forEach(System.out::println);
    }

    @Test
    public void createWithTheSameNumberTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        assertTrue(apartmentList.size() > 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            apartmentDao.create(new Apartment(110, "MEDIUM"));
            apartmentDao.create(new Apartment(110, "MEDIUM"));
        });
    }

    @Test
    public void updateApartmentTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        Assertions.assertNotNull(apartmentList);
        assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentNumber(110);

        apartmentDao.update(apartment);
        Apartment realApartment = apartmentDao.findById(apartment.getApartmentId());
        assertEquals(110, realApartment.getApartmentNumber());
        Assertions.assertEquals(apartment, realApartment);
    }

    @Test
    public void updateApartmentWithTheSameNumberTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentNumber(apartmentList.get(1).getApartmentNumber());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            apartmentDao.update(apartment);
        });
    }

    @Test
    public void deleteApartmentTest() {
        List<Apartment> apartmentList = apartmentDao.findAll();
        assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartmentDao.delete(apartment.getApartmentId());
        List<Apartment> realApartment = apartmentDao.findAll();

        assertEquals(realApartment.size() + 1, apartmentList.size());
    }

}
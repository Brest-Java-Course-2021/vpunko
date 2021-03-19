package com.punko.dao;

import com.punko.model.Apartment;
import java.util.List;

public interface ApartmentDao {

    List<Apartment> findAll();

    Apartment findById(Integer apartmentId);

    Integer create(Apartment apartment);

    Integer update(Apartment apartment);

    Integer delete(Integer apartmentId);

}

package com.punko;

import com.punko.model.Apartment;

import java.util.List;

public interface ApartmentService {

    List<Apartment> findAll();

    Apartment findById(Integer apartmentId);

    Integer create(Apartment apartment);

    Integer update(Apartment apartment);

    Integer delete(Integer apartmentId);

    Integer count();

    /**
     * get Apartments classes
     *
     * @return List String
     */
    List<String> getAllApartmentClass();

}

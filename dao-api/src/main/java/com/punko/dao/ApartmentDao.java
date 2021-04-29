package com.punko.dao;

import com.punko.model.Apartment;

import java.util.List;

public interface ApartmentDao {

    /**
     * find all Apartment
     *
     * @return Apartment list
     */
    List<Apartment> findAll();

    /**
     * find Apartment by Id
     *
     * @return Apartment
     */
    Apartment findById(Integer apartmentId);

    /**
     * create Apartment
     *
     * @param apartment object
     * @return Integer, id new Apartment
     */
    Integer create(Apartment apartment);

    /**
     * update Apartment
     *
     * @param apartment object
     * @return Integer
     */
    Integer update(Apartment apartment);

    /**
     * delete Apartment by Id
     *
     * @return Integer
     */
    Integer delete(Integer apartmentId);

    /**
     * count of Apartments
     *
     * @return Integer count
     */
    Integer count();

    /**
     * get Apartments classes
     *
     * @return List String
     */
    List<String> getAllApartmentClass();
}

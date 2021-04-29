package com.punko.dao;

import com.punko.model.Apartment;
import com.punko.model.Resident;

import java.time.LocalDate;
import java.util.List;

public interface ResidentDao {

    /**
     * find all Resident
     *
     * @return Resident list
     */
    List<Resident> findAll();

    /**
     * create Resident
     *
     * @param resident object
     */
    void create(Resident resident);

    /**
     * find all AllApartmentNumbers
     *
     * @return Apartment list
     */
    List<Apartment> getAllApartmentNumber();

    /**
     * find Resident by Id
     *
     * @return Resident
     */
    Resident findById(Integer id);

    /**
     * update Resident
     *
     * @param resident object
     */
    void updateResident(Resident resident);

    /**
     * delete Resident by Id
     *
     * @return Integer
     */
    Integer delete(Integer id);

    /**
     * find all Resident by time
     *
     * @param arrivalTime, departureTime
     * @return Resident list
     */
    List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime);


    /**
     * count of Resident
     *
     * @return Integer count
     */
    Integer count();

}

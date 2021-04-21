package com.punko;

import com.punko.model.Apartment;
import com.punko.model.Resident;

import java.time.LocalDate;
import java.util.List;

public interface ResidentService {

    List<Resident> findAll();

    void create(Resident resident);

    List<Apartment> getAllApartmentNumber();

    Resident findById(Integer id);

    void update(Resident resident);

    void delete(Integer id);

//    List<Resident> findAllByTime(ResidentSearchByDate residentSearchByDate);

    List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime);

    Integer count();
}

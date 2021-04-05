package com.punko;

import com.punko.model.Apartment;
import com.punko.model.Resident;

import java.util.List;

public interface ResidentService {

    List<Resident> findAll();

    void create(Resident resident);

    List<Apartment> getAllApartmentNumber();

    Resident findById(Integer id);

    void update(Resident resident);

    void delete(Integer id);
}

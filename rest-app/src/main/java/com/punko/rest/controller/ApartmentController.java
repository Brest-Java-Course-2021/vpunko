package com.punko.rest.controller;

import com.punko.ApartmentService;
import com.punko.model.Apartment;
import com.punko.rest.exceptions.apartmentExceptions.ApartmentNoSuchClassException;
import com.punko.rest.exceptions.apartmentExceptions.ApartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/apartments")
    public List<Apartment> findAllRest() {
        return apartmentService.findAll();
    }

    @GetMapping("/apartments/{id}")
    public Apartment findByIdRest(@PathVariable Integer id) {
        //TODO
        Apartment apartment = apartmentService.findById(id);
        if (apartment == null) {
            throw new ApartmentNotFoundException(id);
        }
        return apartment;
    }

    @PostMapping("/apartments")
    public Apartment addApartment(@RequestBody Apartment apartment) {
        List<String> apartmentClasses = apartmentService.getAllApartmentClass();

        if(!apartmentClasses.contains(apartment.getApartmentClass())) {
            throw new ApartmentNoSuchClassException();
        }
        apartmentService.create(apartment);
        return apartment;
    }
}

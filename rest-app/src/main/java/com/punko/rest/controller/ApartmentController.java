package com.punko.rest.controller;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.model.Apartment;
import com.punko.model.dto.ApartmentDto;
import com.punko.rest.exceptions.apartmentExceptions.ApartmentNoSuchClassException;
import com.punko.rest.exceptions.apartmentExceptions.ApartmentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApartmentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ApartmentDtoService apartmentDtoService;

    @GetMapping("/apartments")
    public List<Apartment> findAllRest() {
        LOGGER.debug("Get all apartments");
        return apartmentService.findAll();
    }

    @GetMapping("/apartments/dto")
    public List<ApartmentDto> findAllApartmentDtoRest() {
        LOGGER.debug("Get all Dto apartments");
        return apartmentDtoService.findAllWithAvgTime();
    }

    @GetMapping("/apartments/{id}")
    public Apartment findByIdRest(@PathVariable Integer id) {
        LOGGER.debug("Get apartment by id: {}", id);
        //TODO
        Apartment apartment = apartmentService.findById(id);
        if (apartment == null) {
            LOGGER.debug("Apartment with this id doesn't exist: {}", id);
            throw new ApartmentNotFoundException(id);
        }
        return apartment;
    }

    @PostMapping("/apartments")
    public Apartment addApartment(@RequestBody Apartment apartment) {
        LOGGER.debug("Create apartments: {}", apartment);
        List<String> apartmentClasses = apartmentService.getAllApartmentClass();

        if(!apartmentClasses.contains(apartment.getApartmentClass())) {
            throw new ApartmentNoSuchClassException();
        }
        apartmentService.create(apartment);
        return apartment;
    }

    @PutMapping("/apartments")
    public Apartment updateApartment(@RequestBody Apartment apartment) {
        LOGGER.debug("Update apartments: {}", apartment);
        apartmentService.update(apartment);
        return apartment;
    }

    @DeleteMapping("/apartments/{id}")
    public String deleteApartmentById(@PathVariable Integer id){
        LOGGER.debug("Delete apartment by id: {}", id);
        if (apartmentService.findById(id) == null) {
            LOGGER.debug("Apartment with this id doesn't exist: {}", id);
            throw new ApartmentNotFoundException(id);
        }
        apartmentService.delete(id);
        return "Apartment with id = " + id + " was deleted";
    }
}

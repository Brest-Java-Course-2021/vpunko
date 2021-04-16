package com.punko.rest.controller;

import com.punko.ResidentService;
import com.punko.model.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ResidentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ResidentController.class);

    @Autowired
    ResidentService residentService;

    @GetMapping("/residents")
    public List<Resident> findAll() {
        LOGGER.debug("Get all residents");
        return residentService.findAll();
    }

    @GetMapping("/residents/{id}")
    public Resident findResidentById(@PathVariable Integer id) {
        LOGGER.debug("Get resident by id: {}", id);
        //TODO mistakes
        return residentService.findById(id);
    }

    @PostMapping("/residents")
    public Resident addResident(@RequestBody Resident resident) {
        LOGGER.debug("Add new resident : {}", resident);
        //TODO mistakes
        residentService.create(resident);
        return resident;
    }

    @PutMapping("/residents")
    public Resident updateResident(@RequestBody Resident resident) {
        LOGGER.debug("Update resident : {}", resident);
        //TODO mistakes
        residentService.update(resident);
        return resident;
    }

    @DeleteMapping("/residents/{id}")
    public String deleteResident(@PathVariable Integer id) {
        LOGGER.debug("Delete resident with id: {}", id);
        //TODO mistakes
        residentService.delete(id);
        return "Resident with id = " + id + " was deleted";
    }

    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<List<Resident>> searchAllResidentByDate(
            @RequestParam("arrivalTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalTime,
            @RequestParam("departureTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureTime) {
        List<Resident> residents = residentService.findAllByTime(arrivalTime, departureTime);
        LOGGER.debug("Found residents by date of manufacture with parameters start => {} and end => {} In the amount of {} ", arrivalTime, departureTime, residents.size());
        LOGGER.info("View start URL method GET(REST)  => ( 'transport/search' ) with parameters start => {} and end => {}", arrivalTime, departureTime);
        return new ResponseEntity<>(residents, HttpStatus.FOUND);
    }
}

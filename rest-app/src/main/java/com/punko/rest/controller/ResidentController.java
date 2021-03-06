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
    public ResponseEntity<Resident> findResidentById(@PathVariable Integer id) {
        LOGGER.debug("Get resident by id: {}", id);
        Resident resident = residentService.findById(id);
        return resident != null ? new ResponseEntity<>(resident, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/residents", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Resident> addResident(@RequestBody Resident resident) {
        LOGGER.debug("Add new resident : {}", resident);
        residentService.create(resident);
        return new ResponseEntity<>(resident, HttpStatus.CREATED);
    }

    @PutMapping(value = "/residents", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Resident> updateResident(@RequestBody Resident resident) {
        LOGGER.debug("Update resident : {}", resident);
        residentService.update(resident);
        return new ResponseEntity<>(resident, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/residents/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteResident(@PathVariable Integer id) {
        LOGGER.debug("Delete resident with id: {}", id);
        return new ResponseEntity<>(residentService.delete(id), HttpStatus.OK);
    }

    @GetMapping(value = "/residents/count")
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(residentService.count(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Resident>> searchAllResidentByDate(
            @RequestParam("arrivalTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalTime,
            @RequestParam("departureTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureTime) {
        List<Resident> residents = residentService.findAllByTime(arrivalTime, departureTime);
        LOGGER.debug("Find residents by date, where arrivalTime = {} and end departureTime => {} In the amount of {} ", arrivalTime, departureTime, residents.size());
        return new ResponseEntity<>(residents, HttpStatus.FOUND);
    }

}

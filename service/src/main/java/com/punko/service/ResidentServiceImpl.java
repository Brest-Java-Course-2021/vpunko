package com.punko.service;

import com.punko.ResidentService;
import com.punko.dao.ResidentDao;
import com.punko.model.Apartment;
import com.punko.model.Resident;
import com.punko.model.ResidentSearchByDate.ResidentSearchByDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentServiceImpl.class);

    @Autowired
    ResidentDao residentDao;

    @Override
    public List<Resident> findAll() {
        return residentDao.findAll();
    }

    @Override
    public void create(Resident resident) {
        residentDao.create(resident);
    }

    @Override
    public List<Apartment> getAllApartmentNumber() {
        return residentDao.getAllApartmentNumber();
    }

    @Override
    public Resident findById(Integer id) {
        return residentDao.findById(id);
    }

    @Override
    public void update(Resident resident) {
        residentDao.updateResident(resident);
    }

    @Override
    public void delete(Integer id) {
        residentDao.delete(id);
    }

    @Override
    public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) {
        return residentDao.findAllByTime(arrivalTime, departureTime);
    }
}

package com.punko.service;

import com.punko.ResidentService;
import com.punko.dao.ResidentDao;
import com.punko.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    ResidentDao residentDao;

    @Override
    public List<Resident> findAll() {
        return residentDao.findAll();
    }
}

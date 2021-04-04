package com.punko.service;

import com.punko.ResidentService;
import com.punko.dao.ResidentDao;
import com.punko.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    ResidentDao residentDao;

    @Override
    public List<Resident> findAll() {
        return residentDao.findAll();
    }

    @Override
    public void save(Resident resident) {
        if (resident.getResidentId() == null) {
            create(resident);
        } else {}
    }

    @Override
    public void create(Resident resident) {
        residentDao.create(resident);
    }
}

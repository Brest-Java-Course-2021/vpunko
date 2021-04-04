package com.punko;

import com.punko.model.Resident;

import java.util.List;

public interface ResidentService {

    List<Resident> findAll();

    void save(Resident resident);

    void create(Resident resident);
}

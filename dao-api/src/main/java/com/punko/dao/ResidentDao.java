package com.punko.dao;

import com.punko.model.Resident;

import java.math.BigInteger;
import java.util.List;

public interface ResidentDao {

    List<Resident> findAll();

    void create(Resident resident);
}

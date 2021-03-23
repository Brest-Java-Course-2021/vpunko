package com.punko.dao;

import com.punko.model.Resident;
import com.punko.model.dto.ResidentDto;

import java.util.List;

public interface ResidentDaoDto {

    List<ResidentDto> findAvgDate();

    Long findAvg(Integer id);

    List<ResidentDto> findMaxDate();
}

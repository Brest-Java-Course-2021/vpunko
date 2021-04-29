package com.punko.dao;

import com.punko.model.dto.ApartmentDto;

import java.util.List;

public interface ApartmentDaoDto {

    /**
     * Get all apartment with avg(arrival_time - departure_time)
     * by apartment
     *
     * @return apartment list
     */
    List<ApartmentDto> findAllWithAvgTime();
}

package com.punko;

import com.punko.model.dto.ApartmentDto;

import java.util.List;

public interface ApartmentDtoService {

    /**
     * find all Apartment Dto
     *
     * @return List<ApartmentDto>
     */
    List<ApartmentDto> findAllWithAvgTime();
}

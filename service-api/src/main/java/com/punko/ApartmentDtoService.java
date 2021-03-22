package com.punko;

import com.punko.model.dto.ApartmentDto;

import java.util.List;

public interface ApartmentDtoService {

    List<ApartmentDto> findAllWithAvgTime();
}

package com.punko.service;

import com.punko.ApartmentDtoService;
import com.punko.dao.ApartmentDaoDto;
import com.punko.model.dto.ApartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApartmentDtoServiceImpl implements ApartmentDtoService {

    private final ApartmentDaoDto apartmentDaoDto;

    public ApartmentDtoServiceImpl(ApartmentDaoDto apartmentDaoDto) {
        this.apartmentDaoDto = apartmentDaoDto;
    }

    @Override
    public List<ApartmentDto> findAllWithAvgTime() {
        return apartmentDaoDto.findAllWithAvgTime();
    }
}

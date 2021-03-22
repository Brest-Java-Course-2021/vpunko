package com.punko.dao.jdbc;

import com.punko.dao.ApartmentDaoDto;
import com.punko.model.dto.ApartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApartmentDtoDaoJdbc implements ApartmentDaoDto {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDtoDaoJdbc.class);

//    @Value("${apartment.dto.getAvgTime}")
//    private String findAllWithAvgTimeSQL;

    @Value("${apartment.dto.max.departure.date}")
    private String findAllWithMaxDepDate;

    public ApartmentDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ApartmentDto> findAllWithAvgTime() {
        LOGGER.debug("find all apartments with avgTime");
        return namedParameterJdbcTemplate.query(findAllWithMaxDepDate,
                BeanPropertyRowMapper.newInstance(ApartmentDto.class));
    }
}

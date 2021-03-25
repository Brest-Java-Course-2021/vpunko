package com.punko.dao.jdbc;

import com.punko.dao.ResidentDaoDto;
import com.punko.model.Resident;
import com.punko.model.dto.ResidentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResidentDtoDaoJdbc implements ResidentDaoDto {


    @Value("${resident.avg.diff.time}")
    private String findAllAvgDateSQL;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(ResidentDto.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentDaoJdbc.class);

    public ResidentDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<ResidentDto> findAvgDate() {
        LOGGER.debug("find all avg(Date): ");
        return namedParameterJdbcTemplate.query(findAllAvgDateSQL, rowMapper);
    }

}

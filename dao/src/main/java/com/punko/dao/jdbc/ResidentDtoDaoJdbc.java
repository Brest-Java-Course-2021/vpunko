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

import java.util.List;

@Component
public class ResidentDtoDaoJdbc implements ResidentDaoDto {


    @Value("${resident.avg.diff.time}")
    private String findAllAvgDateSQL;

    @Value("${resident.avg}")
    private String findAvgDate;

    @Value("${resident.max.date}")
    private String findMaxDate;

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

    public Long findAvg(Integer id) {
        LOGGER.debug("find all avg(Date): ");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("RESIDENT_ID", id);
        return namedParameterJdbcTemplate.queryForObject(findAvgDate, sqlParameterSource, Long.class);
    }

    public List<ResidentDto> findMaxDate() {
        LOGGER.debug("find all avg(Date): ");
        return namedParameterJdbcTemplate.query(findMaxDate, rowMapper);
    }
}

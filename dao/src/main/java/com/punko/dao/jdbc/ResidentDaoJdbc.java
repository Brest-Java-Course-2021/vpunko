package com.punko.dao.jdbc;

import com.punko.dao.ResidentDao;
import com.punko.model.Apartment;
import com.punko.model.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Repository
public class ResidentDaoJdbc implements ResidentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentDaoJdbc.class);

    @Value("${resident.select}")
    private String findAlResidentSQL;

    public ResidentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Resident.class);

    @Override
    public List<Resident> findAll() {
        LOGGER.debug("find all resident: ");
        return namedParameterJdbcTemplate.query(findAlResidentSQL, rowMapper);
    }


}

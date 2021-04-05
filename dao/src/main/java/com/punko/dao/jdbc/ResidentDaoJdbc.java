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

    @Value("${resident.create}")
    private String createResidentSQL;

    @Value("${resident.allApartmentNumber}")
    private String getAllApartmentNumberSQL;

    @Value("${resident.check.number}")
    private String checkUniqueEmailSQL;

    @Value("${resident.findById}")
    private String findByIdSQL;

    @Value("${resident.update}")
    private String updateSQL;

    @Value("${resident.delete}")
    private String deleteSQL;


    public ResidentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Resident.class);

    @Override
    public List<Resident> findAll() {
        LOGGER.debug("find all resident");
        return namedParameterJdbcTemplate.query(findAlResidentSQL, rowMapper);
    }

    @Override
    public void create(Resident resident) {
        LOGGER.debug("create resident: {}", resident);
        if (!isEmailUnique(resident)) {
            throw new IllegalArgumentException("Resident with this email already exist");
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("FIRSTNAME", resident.getFirstName())
                            .addValue("LASTNAME", resident.getLastName())
                            .addValue("EMAIL", resident.getEmail())
                            .addValue("ARRIVAL_TIME", resident.getArrivalTime())
                            .addValue("DEPARTURE_TIME", resident.getDepartureTime())
                            .addValue("APARTMENT_NUMBER", resident.getApartmentNumber());
        namedParameterJdbcTemplate.update(createResidentSQL, sqlParameterSource);
    }

    @Override
    public List<Apartment> getAllApartmentNumber() {
        LOGGER.debug("find all apartment Number");
        return namedParameterJdbcTemplate.query(getAllApartmentNumberSQL, rowMapper);
    }

    @Override
    public Resident findById(Integer id) {
        LOGGER.debug("find resident by id: {}", id);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("RESIDENT_ID", id);
        return (Resident) namedParameterJdbcTemplate.queryForObject(findByIdSQL, sqlParameterSource, rowMapper);
    }

    @Override
    public void updateResident(Resident resident) {
        LOGGER.debug("update resident: {}", resident);
        if (!isItTheSameEmail(resident)) {
            throw new IllegalArgumentException("Resident with this email already exist");
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("FIRSTNAME", resident.getFirstName())
                .addValue("LASTNAME", resident.getLastName())
                .addValue("EMAIL", resident.getEmail())
                .addValue("ARRIVAL_TIME", resident.getArrivalTime())
                .addValue("DEPARTURE_TIME", resident.getDepartureTime())
                .addValue("APARTMENT_NUMBER", resident.getApartmentNumber())
                .addValue("RESIDENT_ID", resident.getResidentId());

        namedParameterJdbcTemplate.update(updateSQL, sqlParameterSource);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("delete resident by id: {}", id);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("RESIDENT_ID", id);
        namedParameterJdbcTemplate.update(deleteSQL, sqlParameterSource);
    }

    private boolean isEmailUnique(Resident resident) {
        LOGGER.debug("check is resident email unique: {}", resident);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("EMAIL", resident.getEmail());
        return namedParameterJdbcTemplate.queryForObject(checkUniqueEmailSQL, sqlParameterSource, Integer.class) == 0;
    }

    private boolean isItTheSameEmail(Resident resident) {
        LOGGER.debug("check for update the same email unique: {}", resident);
        String emailFromDB = findById(resident.getResidentId()).getEmail();
        if (emailFromDB.equals(resident.getEmail())) {
            return true;
        } else {
           return isEmailUnique(resident);
        }
    }


}

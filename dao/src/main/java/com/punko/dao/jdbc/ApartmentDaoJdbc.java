package com.punko.dao.jdbc;

import com.punko.dao.ApartmentDao;
import com.punko.model.Apartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

import static com.punko.model.constants.ApartmentClassConst.*;


public class ApartmentDaoJdbc implements ApartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDaoJdbc.class);

    @Value("${apartment.select}")
    private String selectSQL;

    @Value("${apartment.findById}")
    private String findByIdSQL;

    @Value("${apartment.create}")
    private String createSQL;

    @Value("${apartment.check.number}")
    private String checkNumberSQL;

    @Value("${apartment.updateNumber}")
    private String updateApartmentNumberSQL;

    @Value("${apartment.delete}")
    private String deleteApartmentSQL;

    @Value("${apartment.count}")
    private String countSQL;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Apartment.class);

    public ApartmentDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Apartment> findAll() {
        LOGGER.debug("Find all apartment");
        return namedParameterJdbcTemplate.query(selectSQL, rowMapper);
    }

    //Optional?
    @Override
    public Apartment findById(Integer apartmentId) {
        LOGGER.debug("Find by id: {}", apartmentId);
        if (!isApartmentIdCorrect(apartmentId)) {
            LOGGER.debug("Apartment with this id isn't exist: {}", apartmentId);
            return null;}
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_ID", apartmentId);
        return (Apartment) namedParameterJdbcTemplate.queryForObject(findByIdSQL, sqlParameterSource, rowMapper);
    }

    /**
     * add KeyHolder for return id of new Apartment
     */
    @Override
    public Integer create(Apartment apartment) {
//        add KeyHolder for return id of new Apartment
        LOGGER.debug("Create apartment: {}", apartment);
        if (!isTheNumberUnique(apartment)) {
            LOGGER.warn("Apartment with that name is already exist: {}", apartment);
            throw new IllegalArgumentException("Apartment with this number is already exist");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_NUMBER", apartment.getApartmentNumber())
                .addValue("APARTMENT_CLASS", apartment.getApartmentClass());
        namedParameterJdbcTemplate.update(createSQL, sqlParameterSource, keyHolder);
        Integer apartmentId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        apartment.setApartmentId(apartmentId);
        LOGGER.debug("End create method. ApartmentId =: {}", apartmentId);
        return apartmentId;
    }


    //create Final variables for ApartmentClass and check input for them.
    //create check for update number on the exist number
    @Override
    public Integer update(Apartment apartment) {
        LOGGER.debug("Update apartment: {}", apartment);
        if (!isNumberTheSameButDifferentClass(apartment)) {
            LOGGER.warn("Apartment with that number is already exist: {}", apartment);
            throw new IllegalArgumentException("Apartment with that number is already exist");
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_NUMBER", apartment.getApartmentNumber())
                .addValue("APARTMENT_CLASS", apartment.getApartmentClass())
                .addValue("APARTMENT_ID", apartment.getApartmentId());
        return namedParameterJdbcTemplate.update(updateApartmentNumberSQL, sqlParameterSource);
    }

    //made in Resident FK: on delete set null.
    @Override
    public Integer delete(Integer apartmentId) {
        LOGGER.debug("Delete apartment by id: {}", apartmentId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_ID", apartmentId);
        return namedParameterJdbcTemplate.update(deleteApartmentSQL, sqlParameterSource);
    }

    @Override
    public Integer count() {
        LOGGER.debug("count()");
        return namedParameterJdbcTemplate.queryForObject(countSQL, new HashMap<>(), Integer.class);
    }

    @Override
    public List<String> getAllApartmentClass() {
        List<String> apartmentClassList = new ArrayList<>(3);
        apartmentClassList.add(LUXURIOUS);
        apartmentClassList.add(MEDIUM);
        apartmentClassList.add(CHEAP);
        return apartmentClassList;
    }

    private boolean isTheNumberUnique(Apartment apartment) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_NUMBER", apartment.getApartmentNumber());
        return namedParameterJdbcTemplate.queryForObject(checkNumberSQL, sqlParameterSource, Integer.class) == 0;
    }

    private boolean isNumberTheSameButDifferentClass(Apartment apartment) {
        Apartment apartmentFromDB = findById(apartment.getApartmentId());
        if (apartmentFromDB.getApartmentNumber().equals(apartment.getApartmentNumber())) {
            return true;
        }
        return isTheNumberUnique(apartment);

    }

    //don't work
    private boolean isApartmentIdCorrect(int id) {
        List<Apartment> apartmentList = findAll();
        List<Integer> integerList = new ArrayList<>(apartmentList.size());
        for (Apartment apartment : apartmentList) {
            integerList.add(apartment.getApartmentId());
        }
        if (!integerList.contains(id)) {
            LOGGER.debug("isApartmentIdCorrect method. Apartment with this id isn't exist: {}", id);
//            throw new IllegalArgumentException("Apartment with this id is already exist");
            return false;
        }
        return true;
    }



}

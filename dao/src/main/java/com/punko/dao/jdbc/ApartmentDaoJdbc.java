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

import java.util.List;
import java.util.Objects;


public class ApartmentDaoJdbc implements ApartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentDaoJdbc.class);

    @Value("${apartment.select}")
    private String selectSQL;

    @Value("${apartment.findById}")
    private String findByIdSQL;

    @Value("${apartment.create}")
    private String createSQL;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Apartment.class);

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
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_ID", apartmentId);
        Apartment apartment = (Apartment) namedParameterJdbcTemplate.queryForObject(findByIdSQL, sqlParameterSource, rowMapper);
        return apartment;
    }

    /**
     * add KeyHolder for return id of new Apartment
     *
     */
    @Override
    public Integer create(Apartment apartment) {
//        add KeyHolder for return id of new Apartment
        LOGGER.debug("Create apartment: {}", apartment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("APARTMENT_NUMBER", apartment.getApartmentNumber())
                                                .addValue("APARTMENT_CLASS", apartment.getApartmentClass());
        namedParameterJdbcTemplate.update(createSQL, sqlParameterSource, keyHolder);
        Integer apartmentId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        apartment.setApartmentId(apartmentId);
        LOGGER.debug("End create method. ApartmentId =: {}", apartmentId);
        return apartmentId;
    }

    @Override
    public Integer update(Apartment apartment) {
        return null;
    }

    @Override
    public Integer delete(Integer apartmentId) {
        return null;
    }
}

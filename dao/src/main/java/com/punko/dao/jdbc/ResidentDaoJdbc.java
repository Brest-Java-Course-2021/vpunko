package com.punko.dao.jdbc;

import com.punko.dao.ResidentDao;
import com.punko.model.Apartment;
import com.punko.model.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of ResidentDao Interface
 */

@Repository
public class ResidentDaoJdbc implements ResidentDao {

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

    @Value("${resident.find.by.time}")
    private String findByTimeSQL;

    @Value("${resident.count}")
    private String countSQL;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Resident.class);
    RowMapper rowMapperApartment = BeanPropertyRowMapper.newInstance(Apartment.class);

    @Autowired
    public ResidentDaoJdbc(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

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
        if (!isApartmentNumberCorrect(resident)) {
            LOGGER.debug("Apartment with this number doesn't exist: {}", resident.getApartmentNumber());
            throw new IllegalArgumentException("Apartment with this number doesn't exist : "
                    + resident.getApartmentNumber() + ". Please, enter the correct number");
        }
        if (!isArrivalTimeBeforeDepartureTime(resident)) {
            LOGGER.debug("Arrival time should be before than Departure time: {}, {}", resident.getArrivalTime(), resident.getDepartureTime());
            throw new IllegalArgumentException("Arrival time (" + resident.getArrivalTime() + ") should be before than Departure time: (" + resident.getDepartureTime() + ")");
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
    public Resident findById(Integer id) {
        LOGGER.debug("find resident by id: {}", id);
        if (!isResidentIdCorrect(id)) {
            LOGGER.debug("Resident with this id doesn't exist: {}", id);
            throw new IllegalArgumentException("Resident with this id doesn't exist: " + id);
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("RESIDENT_ID", id);
        return (Resident) namedParameterJdbcTemplate.queryForObject(findByIdSQL, sqlParameterSource, rowMapper);
    }

    @Override
    public void updateResident(Resident resident) {
        LOGGER.debug("update resident: {}", resident);
        if (!isItTheSameEmail(resident)) {
            LOGGER.debug("Resident with this email already exist: {}", resident.getEmail());
            throw new IllegalArgumentException("Resident with this email already exist");
        }
        if (!isApartmentNumberCorrect(resident)) {
            LOGGER.debug("Apartment with this number doesn't exist: {}", resident.getApartmentNumber());
            throw new IllegalArgumentException("Apartment with this number doesn't exist : "
                    + resident.getApartmentNumber() + ". Please, enter the correct number");
        }
        if (!isArrivalTimeBeforeDepartureTime(resident)) {
            LOGGER.debug("Arrival time should be before than Departure time: {}, {}", resident.getArrivalTime(), resident.getDepartureTime());
            throw new IllegalArgumentException("Arrival time (" + resident.getArrivalTime() + ") should be before than Departure time: (" + resident.getDepartureTime() + ")");
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
    public Integer delete(Integer id) {
        LOGGER.debug("delete resident by id: {}", id);
        if (!isResidentIdCorrect(id)) {
            LOGGER.debug("Resident with this id doesn't exist: {}", id);
            throw new IllegalArgumentException("Resident with this id doesn't exist: " + id);
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("RESIDENT_ID", id);
        return namedParameterJdbcTemplate.update(deleteSQL, sqlParameterSource);
    }

    @Override
    public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) {
        LOGGER.debug("find resident by time: {}, {}", arrivalTime, departureTime);
        if (departureTime.isBefore(arrivalTime)) {
            LOGGER.warn("searchByTwoDates() throw IllegalArgumentException because departureTime should be later than arrivalTime");
            throw new IllegalArgumentException("departureTime should be later than arrivalTime");
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ARRIVAL_TIME", arrivalTime)
                .addValue("DEPARTURE_TIME", departureTime);
        return namedParameterJdbcTemplate.query(findByTimeSQL, sqlParameterSource, rowMapper);
    }

    @Override
    public List<Apartment> getAllApartmentNumber() {
        LOGGER.debug("find all apartment Number");
        return namedParameterJdbcTemplate.query(getAllApartmentNumberSQL, rowMapperApartment);
    }

    @Override
    public Integer count() {
        LOGGER.debug("count()");
        return namedParameterJdbcTemplate.queryForObject(countSQL, new HashMap<>(), Integer.class);
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

    private boolean isResidentIdCorrect(int id) {
        List<Resident> residentList = findAll();
        List<Integer> integerList = new ArrayList<>(residentList.size());
        for (Resident resident : residentList) {
            integerList.add(resident.getResidentId());
        }
        if (!integerList.contains(id)) {
            LOGGER.debug("isResidentIdCorrect method. Resident with this id doesn't exist: {}", id);
            return false;
        }
        return true;
    }

    private boolean isApartmentNumberCorrect(Resident resident) {
        List<Apartment> apartmentList = namedParameterJdbcTemplate.query(getAllApartmentNumberSQL, rowMapperApartment);
        List<Integer> integerList = new ArrayList<>(apartmentList.size());
        for (Apartment value : apartmentList) {
            integerList.add(value.getApartmentNumber());
        }
        if (!integerList.contains(resident.getApartmentNumber())) {
            LOGGER.debug("isApartmentNumberCorrect method. Apartment with this number doesn't exist: {}", resident.getApartmentNumber());
            return false;
        }
        return true;
    }

    private boolean isArrivalTimeBeforeDepartureTime(Resident resident) {
        return resident.getArrivalTime().isBefore(resident.getDepartureTime());
    }


}

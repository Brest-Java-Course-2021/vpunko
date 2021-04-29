package com.punko.dao.jdbc;

import com.punko.dao.ApartmentDaoDto;
import com.punko.model.dto.ApartmentDto;
import com.punko.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@DataJdbcTest
@Import({ApartmentDtoDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApartmentDtoDaoJdbcTest {

    @Autowired
    ApartmentDaoDto apartmentDaoDto;

    @Test
    public void findAvgDateTest() {
        List<ApartmentDto> residentList = apartmentDaoDto.findAllWithAvgTime();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

}

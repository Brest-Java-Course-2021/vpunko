package com.punko.dao.jdbc;

import com.punko.dao.ResidentDaoDto;
import com.punko.model.Resident;
import com.punko.model.dto.ResidentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class ResidentDtoDaoJdbcTest {

    @Autowired
    ResidentDaoDto residentDaoDto;

    @Test
    public void findAvgDateTest() {
        List<ResidentDto> residentList = residentDaoDto.findAvgDate();
        residentList.stream().forEach(System.out::println);
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

}

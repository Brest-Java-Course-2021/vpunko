package com.punko.dao.jdbc;

import com.punko.dao.ResidentDao;
import com.punko.model.Resident;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class ResidentDaoJdbcTest {

    @Autowired
    private ResidentDao residentDao;

    @Test
    public void findAllResidentTest() {
        List<Resident> residentList = residentDao.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

}

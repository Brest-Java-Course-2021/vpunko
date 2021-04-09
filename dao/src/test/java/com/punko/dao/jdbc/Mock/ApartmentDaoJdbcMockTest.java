package com.punko.dao.jdbc.Mock;


import com.punko.dao.jdbc.ApartmentDaoJdbc;
import com.punko.model.Apartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
@Disabled
public class ApartmentDaoJdbcMockTest {

//    задаем заглушку для namedParameterJdbcTemplate
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    т.к. ApartmentDaoJdbc принимает в конструкторе NPJDBCT, то мы помещаем его в это поле.
//    т.е. вместо контруктора.
    @InjectMocks
    private ApartmentDaoJdbc apartmentDaoJdbc;

    //проверяет на соответствие типа в verify
    @Captor
    private ArgumentCaptor<RowMapper<Apartment>> captor;


    @Test
    public void findAllTest() {

        //устававливаем значение для selectSQL значением select
        String sql = "select";
        ReflectionTestUtils.setField(apartmentDaoJdbc, "selectSQL", sql);

        Apartment apartment = new Apartment();
        List<Apartment> list = new ArrayList<>();
        list.add(apartment);
        //задаем что должен вернуть метод query
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Apartment>>any()))
            .thenReturn(list);

        List<Apartment> result = apartmentDaoJdbc.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertSame(result.get(0), apartment);

        //проверяет был ли вызван метод query c параметрами
        //times проверяет количесвто вызванных раз у метода можно убрать, т.к. у нас только 1 раз
        Mockito.verify(namedParameterJdbcTemplate, Mockito.times(1))
                .query(eq(sql), captor.capture());
        //проверяет, что не вызывалось никаких другигх методов у namedParameterJdbcTemplate
        RowMapper<Apartment> mapper = captor.getValue();
        Assertions.assertNotNull(mapper);
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

//    @Test
//    public void createTest() {
//
//        Apartment apartment = new Apartment();
//
//        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), any(SqlParameterSource.class), eq(Integer.class))).thenReturn(0);
//        apartmentDaoJdbc.create(apartment);
//    }

}

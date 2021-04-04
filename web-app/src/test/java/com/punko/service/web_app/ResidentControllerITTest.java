package com.punko.service.web_app;

import com.punko.ResidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ResidentControllerITTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    ResidentService residentService;

    @Test
    public void shouldReturnResidentsPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/residents")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Residents_list"))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(1)),
                                hasProperty("firstName", is("Stephen")),
                                hasProperty("lastName", is("King")),
                                hasProperty("email", is("stephenking@test.com")),
                                hasProperty("arrivalTime", is(LocalDate.of(2021, 3, 13))),
                                hasProperty("departureTime", is(LocalDate.of(2021, 3, 23))),
                                hasProperty("apartmentNumber", is(101))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(2)),
                                hasProperty("firstName", is("Margaret")),
                                hasProperty("lastName", is("Mitchell")),
                                hasProperty("email", is("margaretmitchell@test.com")),
                                hasProperty("arrivalTime", is(LocalDate.of(2020, 10, 26))),
                                hasProperty("departureTime", is(LocalDate.of(2021, 4, 10))),
                                hasProperty("apartmentNumber", is(102))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(3)),
                                hasProperty("firstName", is("Den")),
                                hasProperty("lastName", is("Brown")),
                                hasProperty("email", is("denbrown@test.com")),
                                hasProperty("arrivalTime", is(LocalDate.of(2021, 2, 13))),
                                hasProperty("departureTime", is(LocalDate.of(2021, 2, 25))),
                                hasProperty("apartmentNumber", is(101))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(4)),
                                hasProperty("firstName", is("Erich")),
                                hasProperty("lastName", is("Remark")),
                                hasProperty("email", is("remark@test.com")),
                                hasProperty("arrivalTime", is(LocalDate.of(2021, 4, 10))),
                                hasProperty("departureTime", is(LocalDate.of(2021, 6, 01))),
                                hasProperty("apartmentNumber", is(102))
                        )
                )))
        ;
    }
}

package com.punko.service.web_app;

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ApartmentControllerITTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnApartmentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartments"))
                .andExpect(model().attribute("apartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(1)),
                                hasProperty("apartmentNumber", is(101)),
                                hasProperty("apartmentClass", is("LUXURY")),
                                hasProperty("avgDifferenceBetweenTime", is(11L))
                        )
                )))
                .andExpect(model().attribute("apartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(2)),
                                hasProperty("apartmentNumber", is(102)),
                                hasProperty("apartmentClass", is("CHEAP")),
                                hasProperty("avgDifferenceBetweenTime", is(108L))
                        )
                )))
                .andExpect(model().attribute("apartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(3)),
                                hasProperty("apartmentNumber", is(105)),
                                hasProperty("apartmentClass", is("MEDIUM")),
                                hasProperty("avgDifferenceBetweenTime", isEmptyOrNullString())
                        )
                )))
    ;
    }

}

package com.punko.service.web_app;

import com.punko.ApartmentService;
import com.punko.model.Apartment;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import static com.punko.model.constants.ApartmentConstants.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
public class ApartmentControllerITTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ApartmentService apartmentService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnApartmentsPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartments"))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(1)),
                                hasProperty("apartmentNumber", is(101)),
                                hasProperty("apartmentClass", is("LUXURIOUS")),
                                hasProperty("avgDifferenceBetweenTime", is(11L))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(2)),
                                hasProperty("apartmentNumber", is(102)),
                                hasProperty("apartmentClass", is("CHEAP")),
                                hasProperty("avgDifferenceBetweenTime", is(109L))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(3)),
                                hasProperty("apartmentNumber", is(105)),
                                hasProperty("apartmentClass", is("MEDIUM")),
                                hasProperty("avgDifferenceBetweenTime", isEmptyOrNullString())
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenNewApartmentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartmentPage"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("apartmentAttribute", isA(Apartment.class)));
    }

    @Test
    public void shouldAddNewApartment() throws Exception {

        Integer countBefore = apartmentService.count();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentNumber", String.valueOf(10))
                        .param("apartmentClass", "MEDIUM")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        //check database size
        Integer countAfter = apartmentService.count();
        Assertions.assertEquals(countBefore + 1, countAfter);
    }

    @Test
    public void shouldOpenEditApartmentPageById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartmentPage"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentId", is(1))))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentNumber", is(101))))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentClass", is("LUXURIOUS"))));
    }

//    @Test
//    public void shouldReturnApartmentPageIfApartmentNotFoundById() throws Exception {
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/apartment/9999")
//        ).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isFound())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("apartments"));
//    }

    @Test
    public void shouldUpdateApartmentAfterEdit() throws Exception {

        String testName = "LUXURIOUS";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("apartmentId", "1")
                .param("apartmentNumber", "101")
                .param("apartmentClass", testName)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        Apartment apartment = apartmentService.findById(1);
        Assertions.assertNotNull(apartment);
        Assertions.assertEquals(testName, apartment.getApartmentClass());
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {

        Integer countBefore= apartmentService.count();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        //check database table size
        Integer countAfter = apartmentService.count();
        Assertions.assertEquals(countBefore - 1, countAfter);
    }


}

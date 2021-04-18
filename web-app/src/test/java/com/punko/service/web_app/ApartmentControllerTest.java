package com.punko.service.web_app;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.model.Apartment;
import com.punko.model.dto.ApartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ApartmentController.class)
public class ApartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApartmentService apartmentService;

    @MockBean
    private ApartmentDtoService apartmentDtoService;

    @Captor
    private ArgumentCaptor<Apartment> captor;

    @Test
    public void shouldReturnApartmentsPage() throws Exception {
        ApartmentDto dto1 = createApartmentDto(1, 101, "LUXURIOUS", 11L);
        ApartmentDto dto2 = createApartmentDto(2, 102, "CHEAP", 109L);
        ApartmentDto dto3 = createApartmentDto(3, 105, "MEDIUM", null);
        Mockito.when(apartmentDtoService.findAllWithAvgTime()).thenReturn(Arrays.asList(dto1, dto2, dto3));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartments"))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto1.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto1.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto1.getApartmentClass())),
                                hasProperty("avgDifferenceBetweenTime", is(dto1.getAvgDifferenceBetweenTime()))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto2.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto2.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto2.getApartmentClass())),
                                hasProperty("avgDifferenceBetweenTime", is(dto2.getAvgDifferenceBetweenTime()))
                        )
                )))
                .andExpect(model().attribute("allApartmentsAttribute", hasItem(
                        allOf(
                                hasProperty("apartmentId", is(dto3.getApartmentId())),
                                hasProperty("apartmentNumber", is(dto3.getApartmentNumber())),
                                hasProperty("apartmentClass", is(dto3.getApartmentClass())),
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
        verifyNoMoreInteractions(apartmentService, apartmentDtoService);
    }

    @Test
    public void shouldAddNewApartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentNumber", String.valueOf(10))
                        .param("apartmentClass", "MEDIUM")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        verify(apartmentService).create(captor.capture());

        Apartment apartment = captor.getValue();
        Assertions.assertEquals(10, apartment.getApartmentNumber());
        Assertions.assertEquals("MEDIUM", apartment.getApartmentClass());
    }

    @Test
    public void shouldOpenEditApartmentPageById() throws Exception {
        Apartment apartment = createApartment(1, 101, "LUXURIOUS");
        when(apartmentService.findById(any())).thenReturn(apartment);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + apartment.getApartmentId())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("apartmentPage"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentId", is(apartment.getApartmentId()))))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentNumber", is(apartment.getApartmentNumber()))))
                .andExpect(model().attribute("apartmentAttribute",
                        hasProperty("apartmentClass", is(apartment.getApartmentClass()))));
    }

    //TODO fix
        @Test
    public void shouldReturnApartmentPageIfApartmentNotFoundById() throws Exception {
        int id = 99999;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + id)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/apartments"));
        verify(apartmentService).findById(id);
    }

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

       verify(apartmentService).update(captor.capture());

       Apartment apartment = captor.getValue();
       Assertions.assertEquals(testName, apartment.getApartmentClass());
    }

    @Test
    public void shouldDeleteApartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        verify(apartmentService).delete(3);
    }

    private Apartment createApartment(int id, int number, String apartmentClass) {
        Apartment apartment = new Apartment();
        apartment.setApartmentId(id);
        apartment.setApartmentNumber(number);
        apartment.setApartmentClass(apartmentClass);
        return apartment;
    }

    private ApartmentDto createApartmentDto(int id, int number, String apartmentClass, Long avgDiffTime) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setApartmentId(id);
        apartmentDto.setApartmentNumber(number);
        apartmentDto.setApartmentClass(apartmentClass);
        apartmentDto.setAvgDifferenceBetweenTime(avgDiffTime);
        return apartmentDto;
    }
}

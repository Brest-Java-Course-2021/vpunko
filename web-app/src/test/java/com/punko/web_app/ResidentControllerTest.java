package com.punko.web_app;

import com.punko.ResidentService;
import com.punko.model.Resident;
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

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResidentController.class)
public class ResidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ResidentService residentService;

    @Captor
    private ArgumentCaptor<Resident> captor;

    @Test
    public void shouldReturnResidentsPage() throws Exception {
        Resident resident1 = new Resident("Stephen", "King", "stephenking@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);
        Resident resident2 = new Resident("Margaret", "Mitchell", "margaretmitchell@test.com", LocalDate.of(2020, 2, 26),
                LocalDate.of(2021, 4, 10), 102);
        Resident resident3 = new Resident("Den", "Brown", "denbrown@test.com", LocalDate.of(2021, 2, 13),
                LocalDate.of(2021, 2, 25), 101);
        Resident resident4 = new Resident("Erich", "Remark", "remark@test.com", LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 6, 1), 102);

        Mockito.when(residentService.findAll()).thenReturn(Arrays.asList(resident1, resident2, resident3, resident4));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/residents")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Residents_list"))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident1.getResidentId())),
                                hasProperty("firstName", is(resident1.getFirstName())),
                                hasProperty("lastName", is(resident1.getLastName())),
                                hasProperty("email", is(resident1.getEmail())),
                                hasProperty("arrivalTime", is(resident1.getArrivalTime())),
                                hasProperty("departureTime", is(resident1.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident1.getApartmentNumber()))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident2.getResidentId())),
                                hasProperty("firstName", is(resident2.getFirstName())),
                                hasProperty("lastName", is(resident2.getLastName())),
                                hasProperty("email", is(resident2.getEmail())),
                                hasProperty("arrivalTime", is(resident2.getArrivalTime())),
                                hasProperty("departureTime", is(resident2.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident2.getApartmentNumber()))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident3.getResidentId())),
                                hasProperty("firstName", is(resident3.getFirstName())),
                                hasProperty("lastName", is(resident3.getLastName())),
                                hasProperty("email", is(resident3.getEmail())),
                                hasProperty("arrivalTime", is(resident3.getArrivalTime())),
                                hasProperty("departureTime", is(resident3.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident3.getApartmentNumber()))
                        )
                )))
                .andExpect(model().attribute("allResidentsAttribute", hasItem(
                        allOf(
                                hasProperty("residentId", is(resident4.getResidentId())),
                                hasProperty("firstName", is(resident4.getFirstName())),
                                hasProperty("lastName", is(resident4.getLastName())),
                                hasProperty("email", is(resident4.getEmail())),
                                hasProperty("arrivalTime", is(resident4.getArrivalTime())),
                                hasProperty("departureTime", is(resident4.getDepartureTime())),
                                hasProperty("apartmentNumber", is(resident4.getApartmentNumber()))
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenNewResidentPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Resident"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("residentAttribute", isA(Resident.class)));
        verify(residentService, times(1)).getAllApartmentNumber();
    }

    @Test
    public void shouldAddNewResident() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/resident")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Stephen")
                        .param("lastName", "King")
                        .param("email", "stephenking@test.com")
                        .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 13)))
                        .param("departureTime", String.valueOf(LocalDate.of(2021, 3, 23)))
                        .param("apartmentNumber", String.valueOf(101))
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/residents"))
                .andExpect(redirectedUrl("/residents"));

        verify(residentService).create(captor.capture());

        Resident resident = captor.getValue();
        Assertions.assertEquals(101, resident.getApartmentNumber());
        Assertions.assertEquals("Stephen", resident.getFirstName());
    }

    @Test
    public void shouldOpenEditResidentPageById() throws Exception {
        Resident resident = createResident(1, "Stephen", "King", "stephenking@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);
        when(residentService.findById(any())).thenReturn(resident);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident/" + resident.getResidentId())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("Resident"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("residentId", is(resident.getResidentId()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("firstName", is(resident.getFirstName()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("lastName", is(resident.getLastName()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("email", is(resident.getEmail()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("arrivalTime", is(resident.getArrivalTime()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("departureTime", is(resident.getDepartureTime()))))
                .andExpect(model().attribute("residentAttribute",
                        hasProperty("apartmentNumber", is(resident.getApartmentNumber()))));
        verify(residentService, times(1)).getAllApartmentNumber();
        verify(residentService).findById(1);
    }

    @Test
    public void shouldReturnResidentPageIfResidentNotFoundById() throws Exception {
        int id = 99999;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident/" + id)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/residents"));
        verify(residentService).findById(id);
    }

    @Test
    public void shouldUpdateResidentAfterEdit() throws Exception {

        String testName = "Alex";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/resident/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("residentId", "1")
                        .param("firstName", testName)
                        .param("lastName", "King")
                        .param("email", "stephenking@test.com")
                        .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 13)))
                        .param("departureTime", String.valueOf(LocalDate.of(2021, 3, 23)))
                        .param("apartmentNumber", String.valueOf(101))
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/residents"))
                .andExpect(redirectedUrl("/residents"));

        verify(residentService).update(captor.capture());

        Resident resident = captor.getValue();
        Assertions.assertEquals(testName, resident.getFirstName());
    }

    @Test
    public void shouldDeleteResident() throws Exception {
        int id = 1;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/resident/" + id + "/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/residents"))
                .andExpect(redirectedUrl("/residents"));

        verify(residentService).delete(id);
    }

    @Test
    void shouldSearchResidentByDateWithCorrectParam() throws Exception {
        mockMvc.perform(get("/search")
                .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 1)))
                .param("departureTime", String.valueOf(LocalDate.of(2021, 4, 20))))
                .andExpect(status().isOk())
                .andExpect(view().name("Residents_list"));
    }

    @Test
    void shouldSearchResidentByDateWithInCorrectParam() throws Exception {
        mockMvc.perform(get("/search")
                .param("arrivalTime", String.valueOf(LocalDate.of(2021, 3, 1)))
                .param("departureTime", String.valueOf(LocalDate.of(2019, 4, 20))))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"));
    }

    private Resident createResident(int id, String firstName, String lastName, String email,
                                    LocalDate arrivalTime, LocalDate departureTime, int apartmentNumber) {
        Resident resident = new Resident();
        resident.setResidentId(id);
        resident.setFirstName(firstName);
        resident.setLastName(lastName);
        resident.setEmail(email);
        resident.setArrivalTime(arrivalTime);
        resident.setDepartureTime(departureTime);
        resident.setApartmentNumber(apartmentNumber);
        return resident;
    }
}

package com.punko.service.web_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punko.ApartmentService;
import com.punko.model.Apartment;
import com.punko.model.dto.ApartmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ApartmentControllerITTest {

    private static final String APARTMENT_DTO_URL = "http://localhost:8090/apartments/dto";
    private static final String APARTMENT_URL = "http://localhost:8090/apartments";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    RestTemplate restTemplate;

     private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldReturnApartmentsPage() throws Exception {
        ApartmentDto dto1 = createApartmentDto(1, 101, "LUXURIOUS", 11L);
        ApartmentDto dto2 = createApartmentDto(2, 102, "CHEAP", 109L);
        ApartmentDto dto3 = createApartmentDto(3, 105, "MEDIUM", null);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(dto1, dto2, dto3)))
                );

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
        mockServer.verify();
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

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("apartmentNumber", String.valueOf(10))
                        .param("apartmentClass", "MEDIUM")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        mockServer.verify();
    }

    @Test
    public void shouldOpenEditApartmentPageById() throws Exception {
        Apartment apartment = createApartment(1, 101, "LUXURIOUS");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL + "/"
                + apartment.getApartmentId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(apartment))
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/1")
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
        mockServer.verify();
    }

    @Test
    public void shouldReturnApartmentPageIfApartmentNotFoundById() throws Exception {
        int id = 99999;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + id)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/apartments"));
    }

    @Test
    public void shouldUpdateApartmentAfterEdit() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

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

        mockServer.verify();
    }

    @Test
    public void shouldDeleteApartment() throws Exception {
        int id = 3;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/apartment/" + id + "/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/apartments"))
                .andExpect(redirectedUrl("/apartments"));

        mockServer.verify();
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
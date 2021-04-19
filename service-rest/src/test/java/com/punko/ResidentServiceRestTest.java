package com.punko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punko.config.TestConfig;
import com.punko.model.Apartment;
import com.punko.model.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.punko.config.TestConfig.APARTMENT_URL;
import static com.punko.config.TestConfig.RESIDENT_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ResidentServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentServiceRestTest.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ResidentService residentService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldFindAllApartments() throws Exception {

        LOGGER.debug("should find all residents()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(RESIDENT_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                createResident(1, "Stephen", "King", "stephenking@test.com", LocalDate.of(2021, 3, 13),
                                        LocalDate.of(2021, 3, 23), 101),
                                createResident(2,"Margaret", "Mitchell", "margaretmitchell@test.com", LocalDate.of(2020, 2, 26),
                                        LocalDate.of(2021, 4, 10), 102))))
                );

        // when
        List<Resident> residents = residentService.findAll();

        // then
        mockServer.verify();
        assertNotNull(residents);
        assertTrue(residents.size() > 0);
    }

    @Test
    public void shouldCreateResident() throws Exception {

        LOGGER.debug("should create resident()");
        // given
        Resident resident = new Resident("Name", "LastName", "namelast@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(RESIDENT_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(resident))
                );
        // when
        residentService.create(resident);

        // then
        mockServer.verify();
        //TODO create return Integer
    }

    @Test
    public void shouldFindResidentById() throws Exception {

        // given
        Integer id = 1;
        Resident resident = new Resident("Name", "LastName", "namelast@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);
        resident.setResidentId(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(RESIDENT_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(resident))
                );

        // when
        Resident realResident = residentService.findById(id);

        // then
        mockServer.verify();
        assertEquals(realResident.getResidentId(), id);
        assertEquals(realResident.getApartmentNumber(), resident.getApartmentNumber());
    }

    @Test
    public void shouldUpdateResident() throws Exception {

        // given
        Integer id = 1;
        Resident resident = new Resident("Name", "LastName", "namelast@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101);
        resident.setResidentId(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(RESIDENT_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(resident))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(RESIDENT_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(resident))
                );

        // when
        residentService.update(resident);
        Resident updatedResident = residentService.findById(id);

        // then
        mockServer.verify();

        assertEquals(updatedResident.getResidentId(), id);
        assertEquals(updatedResident.getApartmentNumber(), resident.getApartmentNumber());
    }

    @Test
    public void shouldDeleteResident() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(RESIDENT_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        residentService.delete(id);
//        TODO delete method return Integer

        // then
        mockServer.verify();
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

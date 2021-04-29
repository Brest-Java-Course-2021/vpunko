//package com.punko;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.punko.config.TestConfig;
//import com.punko.model.Apartment;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.client.ExpectedCount;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.util.Arrays;
//import java.util.List;
//
//import static com.punko.config.TestConfig.APARTMENT_URL;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class ApartmentServiceRestTest {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceRestTest.class);
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Autowired
//    ApartmentService apartmentService;
//
//    private MockRestServiceServer mockServer;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @BeforeEach
//    public void before() {
//        mockServer = MockRestServiceServer.createServer(restTemplate);
//    }
//
//    @Test
//    public void shouldFindAllApartments() throws Exception {
//
//        LOGGER.debug("should find all apartments()");
//        // given
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL)))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(Arrays.asList(
//                                createApartment(0),
//                                createApartment(1))))
//                );
//
//        // when
//        List<Apartment> apartments = apartmentService.findAll();
//
//        // then
//        mockServer.verify();
//        assertNotNull(apartments);
//        assertTrue(apartments.size() > 0);
//    }
//
//    @Test
//    public void shouldCreateApartment() throws Exception {
//
//        LOGGER.debug("should create apartment()");
//        // given
//        Apartment apartment = new Apartment(777, "MEDIUM");
//
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL)))
//                .andExpect(method(HttpMethod.POST))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString("1"))
//                );
//        // when
//        Integer id = apartmentService.create(apartment);
//
//        // then
//        mockServer.verify();
//        assertNotNull(id);
//    }
//
//    @Test
//    public void shouldFindApartmentById() throws Exception {
//
//        // given
//        Integer id = 1;
//        Apartment apartment = new Apartment(777, "MEDIUM");
//        apartment.setApartmentId(id);
//
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL + "/" + id)))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(apartment))
//                );
//
//        // when
//        Apartment realApartment = apartmentService.findById(id);
//
//        // then
//        mockServer.verify();
//        assertEquals(realApartment.getApartmentId(), id);
//        assertEquals(realApartment.getApartmentNumber(), apartment.getApartmentNumber());
//    }
//
//    @Test
//    public void shouldUpdateApartment() throws Exception {
//
//        // given
//        Integer id = 1;
//        Apartment apartment = new Apartment(777, "MEDIUM");
//        apartment.setApartmentId(id);
//
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL)))
//                .andExpect(method(HttpMethod.PUT))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString("1"))
//                );
//
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL + "/" + id)))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(apartment))
//                );
//
//        // when
//        int result = apartmentService.update(apartment);
//        Apartment updatedApartment = apartmentService.findById(id);
//
//        // then
//        mockServer.verify();
//        assertTrue(1 == result);
//
//
//        assertEquals(updatedApartment.getApartmentId(), id);
//        assertEquals(updatedApartment.getApartmentNumber(), apartment.getApartmentNumber());
//    }
//
//    @Test
//    public void shouldDeleteApartment() throws Exception {
//
//        // given
//        Integer id = 1;
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_URL + "/" + id)))
//                .andExpect(method(HttpMethod.DELETE))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString("1"))
//                );
//        // when
//        int result = apartmentService.delete(id);
//
//        // then
//        mockServer.verify();
//        assertTrue(1 == result);
//    }
//
//    private Apartment createApartment(int index) {
//        Apartment apartment = new Apartment();
//        apartment.setApartmentId(index);
//        apartment.setApartmentNumber(index * 5);
//        apartment.setApartmentClass("MEDIUM");
//        return apartment;
//    }
//}

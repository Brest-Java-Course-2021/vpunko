package com.punko.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.punko.model.Apartment;
import com.punko.rest.exceptions.CustomGlobalExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.punko.model.constants.ApartmentClassConst.MEDIUM;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApartmentControllerTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentControllerTestIT.class);

    public static final String APARTMENT_ENDPOINT = "/apartments";

    @Autowired
    private ApartmentController apartmentController;

    @Autowired
    private CustomGlobalExceptionHandler exceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockApartmentService apartmentService = new MockApartmentService();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(apartmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(exceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllApartmentsRest() throws Exception {
        LOGGER.debug("should find all Apartments test");
        List<Apartment> apartmentList = apartmentService.findAll();
        assertNotNull(apartmentList);
        assertTrue(apartmentList.size() > 0);
    }

    @Test
    public void shouldFindByIdTest() throws Exception {
        LOGGER.debug("should find Apartment by id test");
        List<Apartment> apartmentList = apartmentService.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);

        Integer apartmentId = apartmentList.get(0).getApartmentId();
        Apartment apartment = apartmentService.findById(apartmentId);
        Assertions.assertNotNull(apartment);
        Assertions.assertEquals(apartment.getApartmentNumber(), apartmentList.get(0).getApartmentNumber());
    }

    @Test
    public void shouldReturnNotFoundOnMissedApartment() throws Exception {
        LOGGER.debug("should return NotFound on missed Apartment test");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(
                APARTMENT_ENDPOINT + "/999999")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity())
                .andReturn().getResponse();
        assertNotNull(response);
    }

    @Test
    public void shouldCreateTest() throws Exception {
        LOGGER.debug("should create Apartment by id test");

        Integer countBeforeCreate = apartmentService.count();
        apartmentService.create(new Apartment(110, MEDIUM));
        Integer countAfterCreate = apartmentService.count();
        Assertions.assertEquals(countBeforeCreate + 1, countAfterCreate);
    }

    @Test
    public void createApartmentWithSameNumberTest() throws Exception {
        LOGGER.debug("create Apartment with the same number test");

        apartmentService.create(new Apartment(1, MEDIUM));

        String json = objectMapper.writeValueAsString(new Apartment(1, MEDIUM));
        MockHttpServletResponse response =
                mockMvc.perform(post(APARTMENT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();
        assertNotNull(response);
    }


    @Test
    public void shouldUpdateApartmentTest() throws Exception {
        LOGGER.debug("should update apartment()");
        List<Apartment> apartmentList = apartmentService.findAll();
        Assertions.assertNotNull(apartmentList);
        Assertions.assertTrue(apartmentList.size() > 0);

        Apartment apartment = apartmentList.get(0);
        apartment.setApartmentNumber(200);
        apartmentService.update(apartment);

        Apartment realApartment = apartmentService.findById(apartment.getApartmentId());
        Assertions.assertEquals(200, realApartment.getApartmentNumber());
        Assertions.assertEquals(apartment, realApartment);
    }

    @Test
    public void shouldDeleteApartmentTest() throws Exception {
        LOGGER.debug("should delete Apartment()");
        List<Apartment> apartmentList = apartmentService.findAll();
        Assertions.assertTrue(apartmentList.size() > 0);

        Integer countBeforeDelete = apartmentService.count();
        apartmentService.delete(apartmentList.get(0).getApartmentId());
        Integer countAfterDelete = apartmentService.count();
        Assertions.assertEquals(countBeforeDelete, countAfterDelete + 1);
    }

    @Test
    public void shouldReturnNotFoundOnDeleteMissedApartment() throws Exception {

        LOGGER.debug("shouldReturnNotFoundOnDeleteMissedDepartment()");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete(
                APARTMENT_ENDPOINT + "/999999")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError())
                .andReturn().getResponse();
        assertNotNull(response);
    }


    //////////////////////////////////////////////////////////
    private class MockApartmentService {

        public List<Apartment> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(APARTMENT_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            //парсим json into string
            return objectMapper.readValue(response.getContentAsString(),
                    new TypeReference<List<Apartment>>() {
                    });
        }

        public Apartment findById(Integer apartmentId) throws Exception {
            LOGGER.debug("findById({})", apartmentId);
            MockHttpServletResponse response = mockMvc
                    .perform(get(APARTMENT_ENDPOINT + "/" + apartmentId)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Apartment.class);
        }


        public Integer create(Apartment apartment) throws Exception {
            LOGGER.debug("create({})", apartment);
            String json = objectMapper.writeValueAsString(apartment);
            MockHttpServletResponse response =
                    mockMvc.perform(post(APARTMENT_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer update(Apartment apartment) throws Exception {
            LOGGER.debug("update({})", apartment);
            MockHttpServletResponse response =
                    mockMvc.perform(put(APARTMENT_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(apartment))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer delete(Integer departmentId) throws Exception {
            LOGGER.debug("delete(id:{})", departmentId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(APARTMENT_ENDPOINT).append("/")
                            .append(departmentId).toString())
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer count() throws Exception {
            LOGGER.debug("count()");
            MockHttpServletResponse response = mockMvc.perform(get(APARTMENT_ENDPOINT + "/count")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public List<String> getAllApartmentClass() {
            return null;
        }
    }
}
package com.punko.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.punko.model.Resident;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ResidentControllerTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResidentControllerTestIT.class);

    public static final String RESIDENT_ENDPOINT = "/residents";

    @Autowired
    private ResidentController residentController;

    @Autowired
    private CustomGlobalExceptionHandler exceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockAResidentService residentService = new MockAResidentService();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(residentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(exceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllResidentsRest() throws Exception {
        LOGGER.debug("should find all Resident test");
        List<Resident> residentList = residentService.findAll();
        assertNotNull(residentList);
        assertTrue(residentList.size() > 0);
    }

    @Test
    void shouldSearchResidentsRestByDate() throws Exception {
        LOGGER.debug("should search Resident by date test");

        LocalDate arrivalTime = LocalDate.of(2021, 3, 1);
        LocalDate departureTime = LocalDate.of(2021, 4, 23);

        List<Resident> residentList = residentService.findAllByTime(arrivalTime, departureTime);
        assertNotNull(residentList);
        assertTrue(residentList.size() > 0);
    }

    @Test
    void shouldSearchResidentsRestByDateIncorrectParam() throws Exception {
        LOGGER.debug("should search Resident by date incorrect param test");

        LocalDate arrivalTime = LocalDate.of(2021, 3, 1);
        LocalDate departureTime = LocalDate.of(2019, 4, 23);

        String searchUrl = "/search?arrivalTime=" + arrivalTime + "&departureTime=" + departureTime;
        MockHttpServletResponse response = mockMvc.perform(get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError())
                .andReturn().getResponse();

        assertNotNull(response);
    }

    @Test
    public void shouldFindByIdTest() throws Exception {
        LOGGER.debug("should find Resident by id test");
        List<Resident> residentList = residentService.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);

        Integer residentId = residentList.get(0).getResidentId();
        Resident resident = residentService.findById(residentId);
        Assertions.assertNotNull(resident);
        Assertions.assertEquals(resident.getFirstName(), residentList.get(0).getFirstName());
    }

    @Test
    public void shouldCreateTest() throws Exception {
        LOGGER.debug("should create Resident by id test");

        Integer countBeforeCreate = residentService.count();
        residentService.create(new Resident("Name", "lastName", "nameLastname@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101));
        Integer countAfterCreate = residentService.count();
        Assertions.assertEquals(countBeforeCreate + 1, countAfterCreate);
    }

    @Test
    public void createApartmentWithSameEmailTest() throws Exception {
        LOGGER.debug("create Resident with the same email test");

        residentService.create(new Resident("Name", "lastName", "nameLastname@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101));

        String json = objectMapper.writeValueAsString(new Resident("Name", "lastName", "nameLastname@test.com", LocalDate.of(2021, 3, 13),
                LocalDate.of(2021, 3, 23), 101));
        MockHttpServletResponse response =
                mockMvc.perform(post(RESIDENT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();
        assertNotNull(response);
    }

    @Test
    public void shouldUpdateResidentTest() throws Exception {
        LOGGER.debug("should update resident()");
        List<Resident> residentList = residentService.findAll();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);

        Resident resident = residentList.get(0);
        resident.setFirstName("testName");
        residentService.update(resident);

        Resident realApartment = residentService.findById(resident.getResidentId());
        Assertions.assertEquals("testName", realApartment.getFirstName());
        Assertions.assertEquals(resident, realApartment);
    }

    @Test
    public void shouldDeleteResidentTest() throws Exception {
        LOGGER.debug("should delete Resident()");
        List<Resident> residentList = residentService.findAll();
        Assertions.assertTrue(residentList.size() > 0);

        Integer countBeforeDelete = residentService.count();
        int id = residentList.get(0).getResidentId();
        residentService.delete(id);
        Integer countAfterDelete = residentService.count();
        Assertions.assertEquals(countBeforeDelete, countAfterDelete + 1);
    }

    @Test
    public void shouldReturnNotFoundOnDeleteMissedApartment() throws Exception {
        LOGGER.debug("should return not found on delete missed Resident()");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete(
                RESIDENT_ENDPOINT + "/999999")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError())
                .andReturn().getResponse();
        assertNotNull(response);
    }

    ///////////////////////////////////////////////////////////////////////

    private class MockAResidentService {

        public List<Resident> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(RESIDENT_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            //парсим json into string
            return objectMapper.readValue(response.getContentAsString(),
                    new TypeReference<List<Resident>>() {
                    });
        }

        public List<Resident> findAllByTime(LocalDate arrivalTime, LocalDate departureTime) throws Exception {
            LOGGER.debug("findAllByTime() {}, {}", arrivalTime, departureTime);
            //without http/local
            String searchUrl = "/search?arrivalTime=" + arrivalTime + "&departureTime=" + departureTime;
            MockHttpServletResponse response = mockMvc.perform(get(searchUrl)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is3xxRedirection())
                    .andReturn().getResponse();
            assertNotNull(response);
            return objectMapper.readValue(response.getContentAsString(),
                    new TypeReference<List<Resident>>() {
                    });
        }

        public Resident findById(Integer residentId) throws Exception {
            LOGGER.debug("findById({})", residentId);
            MockHttpServletResponse response = mockMvc
                    .perform(get(RESIDENT_ENDPOINT + "/" + residentId)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Resident.class);
        }

        public Void create(Resident resident) throws Exception {
            LOGGER.debug("create({})", resident);
            String json = objectMapper.writeValueAsString(resident);
            MockHttpServletResponse response =
                    mockMvc.perform(post(RESIDENT_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Void.class);
        }

        public Void update(Resident resident) throws Exception {
            LOGGER.debug("update({})", resident);
            MockHttpServletResponse response =
                    mockMvc.perform(put(RESIDENT_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(resident))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Void.class);
        }

        public Integer delete(Integer residentId) throws Exception {
            LOGGER.debug("delete(id:{})", residentId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(RESIDENT_ENDPOINT).append("/")
                            .append(residentId).toString())
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            String res = response.getContentAsString();
            assertNotNull(res);
            return objectMapper.readValue(res, Integer.class);
        }

        public Integer count() throws Exception {
            LOGGER.debug("count()");
            MockHttpServletResponse response = mockMvc.perform(get(RESIDENT_ENDPOINT + "/count")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }

}

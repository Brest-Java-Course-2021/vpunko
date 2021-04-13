package com.punko.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.punko.model.dto.ApartmentDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApartmentControllerDTOTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentControllerTestIT.class);

    public static final String APARTMENT_DTO_ENDPOINT = "/apartments/dto";

    @Autowired
    private ApartmentController apartmentController;

    @Autowired
    private CustomGlobalExceptionHandler exceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockApartmentDtoService apartmentDtoService = new MockApartmentDtoService();

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
    public void findAvgDateTest() throws Exception {
        List<ApartmentDto> residentList = apartmentDtoService.findAllWithAvgTime();
        Assertions.assertNotNull(residentList);
        Assertions.assertTrue(residentList.size() > 0);
    }

    private class MockApartmentDtoService {

        public List<ApartmentDto> findAllWithAvgTime() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(APARTMENT_DTO_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<ApartmentDto>>() {
            });
        }
    }

}

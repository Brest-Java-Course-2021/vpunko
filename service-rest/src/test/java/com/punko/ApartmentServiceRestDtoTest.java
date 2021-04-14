package com.punko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punko.config.TestConfig;
import com.punko.model.Apartment;
import com.punko.model.dto.ApartmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ApartmentServiceRestDtoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceRestDtoTest.class);

    public static final String APARTMENT_DTO_URL = "http://localhost:8090/apartments/dto";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ApartmentDtoService apartmentDtoService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void findAllWithAvgTime() throws Exception {
        LOGGER.debug("should find all Apartments dto()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(APARTMENT_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                createApartmentDto(0),
                                createApartmentDto(1))))
                );

        // when
        List<ApartmentDto> apartmentDtos = apartmentDtoService.findAllWithAvgTime();

        // then
        mockServer.verify();
        assertNotNull(apartmentDtos);
        assertTrue(apartmentDtos.size() > 0);
    }

    private ApartmentDto createApartmentDto(int index) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setApartmentId(index);
        apartmentDto.setApartmentNumber(index * 5);
        apartmentDto.setApartmentClass("MEDIUM");
        return apartmentDto;
    }
}

package com.punko.service.web_app;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.dao.ApartmentDaoDto;
import com.punko.model.dto.ApartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ApartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    private final ApartmentService apartmentService;

    private final ApartmentDtoService apartmentDtoService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService, ApartmentDtoService apartmentDtoService) {
        this.apartmentService = apartmentService;
        this.apartmentDtoService = apartmentDtoService;
    }

    @GetMapping(value = "/apartments")
    public final String apartments(Model model) {
        LOGGER.debug("apartments()");
        List<ApartmentDto> apartmentDaoDtoList = apartmentDtoService.findAllWithAvgTime();
        model.addAttribute("allApartments", apartmentDaoDtoList);
        return "apartments";
    }

}

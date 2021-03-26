package com.punko.service.web_app;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("apartments", apartmentDtoService.findAllWithAvgTime());
        return "apartments";
    }

}

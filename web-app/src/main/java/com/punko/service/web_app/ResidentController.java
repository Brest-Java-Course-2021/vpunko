package com.punko.service.web_app;

import com.punko.ResidentService;
import com.punko.model.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ResidentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    ResidentService residentService;

    @GetMapping("/residents")
    public String getAllResident(Model model) {
        LOGGER.debug("apartments()");
        List<Resident> residentList = residentService.findAll();
        model.addAttribute("allResidents", residentList);
        return "Residents list";
    }
}

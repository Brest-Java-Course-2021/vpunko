package com.punko.service.web_app;

import com.punko.ResidentService;
import com.punko.model.Resident;
import com.punko.model.ResidentSearchByDate.ResidentSearchByDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ResidentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    ResidentService residentService;

    @GetMapping("/residents")
    public String getAllResident(Model model) {
        LOGGER.debug("find all residents()");
        List<Resident> residentList = residentService.findAll();
        model.addAttribute("allResidentsAttribute", residentList);
        model.addAttribute(new ResidentSearchByDate());
        return "Residents_list";
    }

    @GetMapping("/resident")
    public String gotoAddResidentPage(Model model) {
        LOGGER.debug("go to add resident page");
        model.addAttribute("residentAttribute", new Resident());
        model.addAttribute("allApartmentNumbers", residentService.getAllApartmentNumber());
        model.addAttribute("isNew", true);
        return "Resident";
    }

    @PostMapping("/resident")
    public String addResident(@Valid @ModelAttribute("residentAttribute") Resident resident, BindingResult bindingResult, Model model)  {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allApartmentNumbers", residentService.getAllApartmentNumber());
            model.addAttribute("isNew", true);
            return "Resident";
        }
        LOGGER.debug("save resident: {}", resident);
        residentService.create(resident);
        return "redirect:/residents";
    }

    @GetMapping("/resident/{id}")
    public String gotoEditResidentPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("go to Edit Resident Page({})", id);
        Resident resident = residentService.findById(id);
        model.addAttribute("residentAttribute", resident);
        model.addAttribute("allApartmentNumbers", residentService.getAllApartmentNumber());
        model.addAttribute("isNew", false);
        return "Resident";
    }

    @PostMapping("/resident/{id}")
    public String editResident(@Valid @ModelAttribute("residentAttribute") Resident resident, BindingResult bindingResult, Model model)  {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allApartmentNumbers", residentService.getAllApartmentNumber());
            model.addAttribute("isNew", false);
            return "Resident";
        }
        LOGGER.debug("edit resident: {}", resident);
        residentService.update(resident);
        return "redirect:/residents";
    }

    @GetMapping("/resident/{id}/delete")
    public String deleteResident(@PathVariable Integer id) {
        LOGGER.debug("delete resident with id = {}", id);
        residentService.delete(id);
        return "redirect:/residents";
    }


    @GetMapping("/residents/search")
    public String searchAllResidentByDate(@RequestParam("arrivalTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalTime,
                                          @RequestParam("departureTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureTime,
                                          Model model) {
        LOGGER.debug("search residents by date()");
        List<Resident> residentListByTime = residentService.findAllByTime(arrivalTime, departureTime);
        model.addAttribute("allResidentsAttribute", residentListByTime);
        return "Residents_list";
    }


}

package com.punko.web_app;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.model.Apartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ApartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    private final ApartmentDtoService apartmentDtoService;

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentDtoService apartmentDtoService, ApartmentService apartmentService) {
        this.apartmentDtoService = apartmentDtoService;
        this.apartmentService = apartmentService;
    }

    /**
     * Goto apartments list page.
     *
     * @return view name
     */
    @GetMapping(value = "/apartments")
    public final String apartments(Model model) {
        LOGGER.debug(" show all apartments controller");
        model.addAttribute("allApartmentsAttribute", apartmentDtoService.findAllWithAvgTime());
        return "apartments";
    }

    /**
     * Goto new apartment page.
     *
     * @return view name
     */
    @GetMapping("/apartment")
    public final String gotoAddApartmentPage(Model model) {
        LOGGER.debug("gotoAddApartmentPage");
        model.addAttribute("isNew", true);
        model.addAttribute("apartmentAttribute", new Apartment());
        return "apartmentPage";
    }

    /**
     * Goto add apartment.
     *
     * @return redirect all apartments
     */
    @PostMapping(value = "/apartment")
    public String addApartment(@Valid @ModelAttribute("apartmentAttribute") Apartment apartment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "apartmentPage";
        }
        LOGGER.debug("addApartment({})", apartment);
        apartmentService.create(apartment);
        return "redirect:/apartments";
    }

    /**
     * Goto edit apartment page.
     *
     * @return view name
     */
    @GetMapping(value = "/apartment/{id}")
    public final String gotoEditApartmentPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditApartmentPAge({})", id);
        Apartment apartment = apartmentService.findById(id);
        if (apartment == null) {
            return "redirect:/apartments";
        }
        model.addAttribute("isNew", false);
        model.addAttribute("apartmentAttribute", apartment);
        return "apartmentPage";
    }


    @PostMapping(value = "/apartment/{id}")
    public String updateApartment(@Valid @ModelAttribute("apartmentAttribute") Apartment apartment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "apartmentPage";
        }
        LOGGER.debug("update apartment({})", apartment);
        apartmentService.update(apartment);
        return "redirect:/apartments";
    }

    @GetMapping(value = "/apartment/{id}/delete")
    public String deleteApartmentById(@PathVariable Integer id) {
        LOGGER.debug("delete apartment {}", id);
        apartmentService.delete(id);
        return "redirect:/apartments";
    }

}
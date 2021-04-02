package com.punko.service.web_app;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.model.Apartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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
        model.addAttribute("apartmentsAttribute", apartmentDtoService.findAllWithAvgTime());
        return "apartments";
    }

    /**
     * Goto edit apartment page.
     *
     * @return view name
     */
    @GetMapping(value = "/apartment/{id}")
    public final String gotoEditApartmentPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditApartmentPAge({},{})", id, model);
        Apartment apartment = apartmentService.findById(id);
            model.addAttribute("isNew", false);
            model.addAttribute("apartmentAttribute", apartment);
            return "apartmentPage";
    }

    /**
     * Goto new apartment page.
     *
     * @return view name
     */
    @GetMapping("/apartment")
    public final String gotoAddApartmentPage(Model model) {
        LOGGER.debug("gotoAddApartmentPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("apartmentAttribute", new Apartment());
        return "apartmentPage";
    }


    @PostMapping(value = "/apartment")
    public String addApartment(Apartment apartment) {
        LOGGER.debug("addApartment({})", apartment);
        apartmentService.create(apartment);
        return "redirect:/apartments";
    }

    @PostMapping(value = "/apartment/{id}")
        public String updateApartment(Apartment apartment) {
            LOGGER.debug("update apartment({})", apartment);
            apartmentService.update(apartment);
            return "redirect:/apartments";
        }

    @GetMapping(value = "/apartment/{id}/delete")
     public String deleteApartmentById(@PathVariable Integer id, Model model){
        LOGGER.debug("delete apartment ({}, {})", id, model);
        apartmentService.delete(id);
        return "redirect:/apartments";
    }



}

//    @Autowired
//    ApartmentService apartmentService;
//
//    @RequestMapping("")
//    public String showAllApartment(Model model) {
//        List<Apartment> apartmentList = apartmentService.findAll();
//        model.addAttribute("allApartments", apartmentList);
//
//        return "apartments";
//    }
//
//    @RequestMapping("/addApartment")
//    public String addApartment(Model model) {
//        Apartment apartment = new Apartment();
//        model.addAttribute("addApartmentAttribute", apartment);
//        return "apartmentPage";
//    }
//
//    @RequestMapping("/saveApartment")
//    public String saveApartment(@ModelAttribute("addApartmentAttribute") Apartment apartment) {
//        apartmentService.create(apartment);
//        return "redirect:/apartments";
//    }
//
//    /**
//     *  name of attribute must be the same like in addApartment
//     *  because they return the same jsp page
//     */
//    @RequestMapping("/updateApartment/{id}")
//    public String updateApartment(@PathVariable int id, Model model) {
//        Apartment apartment = apartmentService.findById(id);
//        model.addAttribute("addApartmentAttribute", apartment);
//        return "apartmentPage";
//    }
//
//    @RequestMapping("/deleteApartment/{id}")
//    public String deleteApartment(@PathVariable int id) {
//        apartmentService.delete(id);
//        return "redirect:/apartments";
//    }
//}


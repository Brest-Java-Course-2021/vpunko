package com.punko.service.web_app;

import com.punko.ApartmentDtoService;
import com.punko.ApartmentService;
import com.punko.dao.ApartmentDaoDto;
import com.punko.model.Apartment;
import com.punko.model.dto.ApartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ApartmentController {

    /**
     * Goto apartments list page.
     *
     * @return view name
     */
    @GetMapping(value = "/apartments")
    public final String apartments(Model model) {
        return "apartments";
    }

    /**
     * Goto edit apartment page.
     *
     * @return view name
     */
    @GetMapping("/apartment/{id}")
    public final String gotoEditApartmentPage(@PathVariable Integer id, Model model) {
        return "apartmentPage";
    }

    @GetMapping("/apartment/add")
    public final String gotoAddApartmentPage(Model model) {
        return "apartmentPage";
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


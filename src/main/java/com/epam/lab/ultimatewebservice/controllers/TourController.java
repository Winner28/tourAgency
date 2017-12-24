package com.epam.lab.ultimatewebservice.controllers;


import com.epam.lab.ultimatewebservice.entity.Permission;
import com.epam.lab.ultimatewebservice.entity.Tour;
import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.TourService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tours")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TourController {

    private final TourService tourService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createTourPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tour/createTour");
        modelAndView.addObject("tour", new Tour());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String tourCreation(@ModelAttribute("tour") Tour tour, Model model,
                               HttpServletRequest request) {
        String duration = request.getParameter("duration");
        System.out.println(duration);
        String isHot = request.getParameter("hot");
        String isActive = request.getParameter("active");
        if (!checkValidation(tour) || isHot == null || isActive == null || Integer.getInteger(duration, null) == null) {
            model.addAttribute("errorMessage", "Error when we try to create tour, some fields are empty");
            return "tour/error";
        }

        Tour createdTour = tourService.createTour(tour);
        if (createdTour == null) {
            model.addAttribute("errorMessage", "Error when we try to create tour");
            return "tour/error";
        }
        model.addAttribute("tour", createdTour);
        model.addAttribute("message", "User successfully created!");
        return "tour/showTour";
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTourById(@PathVariable String id) {
        Tour tourToDelete = tourService.getTourById(Integer.parseInt(id));
        if (tourToDelete == null) {
            return new ModelAndView("tour/error", "errorMessage",
                    "Tour with such id doesn't exist");
        }
            if (!tourService.deleteTourById(Integer.parseInt(id))) {
                return new ModelAndView("tour/error", "errorMessage",
                        "Error while deleting tour with such id");
            }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tour/showTour");
        modelAndView.addObject("tour", tourToDelete);
        modelAndView.addObject("message", "Tour with ID = " +
                tourToDelete.getId() + " successfully deleted!");
        return modelAndView;
    }



    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateTourPage(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        Tour tour = tourService.getTourById(id);
        if (tour == null) {
            modelAndView.setViewName("tour/error");
            modelAndView.addObject("errorMessage", "There is no tour with such id");
            return modelAndView;
        }
        modelAndView.setViewName("tour/updateTour");
        modelAndView.addObject("tour", tour);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateTourPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            modelAndView.setViewName("tour/error");
            modelAndView.addObject("errorMessage", "We dont have tour with such id");
            return modelAndView;
        }
        Tour tour = tourService.getTourById(Integer.parseInt(id));
        if (tour == null) {
            modelAndView.setViewName("tour/error");
            modelAndView.addObject("errorMessage", "We dont have tour with such id");
            return modelAndView;
        }
        modelAndView.setViewName("tour/updateTour");
        modelAndView.addObject("tour", tour);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTour(@ModelAttribute Tour tour, Model model,
                             HttpServletRequest request) {
        tour.setId(Integer.parseInt(request.getParameter("id")));
        Tour updatedTour = tourService.updateTour(tour);
        String isHot = request.getParameter("hot");
        String isActive = request.getParameter("active");
        if (updatedTour == null) {
            model.addAttribute("errorMessage", "Error when we try to update tour");
            return "tour/error";
        }
        if (!checkValidation(tour) || isActive != null || isHot != null) {
            model.addAttribute("errorMessage","please fill all fields");
            return "tour/error";
        }
        model.addAttribute("message", "Tour successfully Updated!");
        model.addAttribute("tour", updatedTour);
        return "tour/showTour";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllTours() {
        ModelAndView modelAndView = new ModelAndView();
        List<Tour> tourList = tourService.getTourList();
        if (tourList == null) {
            modelAndView.setViewName("tour/error");
            modelAndView.addObject("errorMessage", "THERE IS NULL");
            return modelAndView;
        }
        modelAndView.setViewName("tour/showAllTours");
        modelAndView.addObject("tourList", tourList);
        return modelAndView;
    }

    private ModelAndView checkTourAndReturnModel(Tour tour) {
        ModelAndView model = new ModelAndView();
        if (tour == null) {
            model.setViewName("tour/error");
            model.addObject("errorMessage", "Tour doesn't exist");
            return model;
        }
        model.setViewName("tour/showTour");
        model.addObject("tour", tour);
        return model;
    }
    private boolean checkValidation(Tour tour) {
        return tour.getPrice() != 0  && tour.getDuration() != 0 &&
                tour.getAgentId() != 0 && tour.getTourTypesId() != 0;

    }
 }

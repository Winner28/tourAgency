package com.epam.lab.ultimatewebservice.controllers;


import com.epam.lab.ultimatewebservice.entity.Tour;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/tours")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TourController {

    private final TourService tourService;
    private static final String LOGGED_COOKIE = "userLoggedIn";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createTourPage(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tour/createTour");
        modelAndView.addObject("tour", new Tour());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String tourCreation(@ModelAttribute("tour") Tour tour, Model model,
                               HttpServletRequest request) {
        if (!checkAccess(request)) {
            model.addAttribute("errorMessage", "Bad access. Your request denied");
            return "errors/error";
        }
        if (!validateTour(tour)) {
            model.addAttribute("errorMessage", "Bad input info");
            return "errors/error";
        }
        Tour createdTour = tourService.createTour(tour);
        if (createdTour == null) {
            model.addAttribute("errorMessage", "Error when we try to create tour");
            return "errors/error";
        }
        return "tour/showTour";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTourById(@PathVariable int id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        Tour tourToDelete = tourService.getTourById(id);
        if (tourToDelete == null) {
            return new ModelAndView("errors/error", "errorMessage",
                    "Tour with such id doesn't exist");
        }
        if (!tourService.deleteTourById(id)) {
            return new ModelAndView("errors/error", "errorMessage",
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
    public ModelAndView updateTourPage(@PathVariable int id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Tour tour = tourService.getTourById(id);
        if (tour == null) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "There is no tour with such id");
            return modelAndView;
        }
        modelAndView.setViewName("tour/updateTour");
        modelAndView.addObject("tour", tour);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTour(@ModelAttribute Tour tour, Model model,
                             HttpServletRequest request) {
        if (!checkAccess(request)) {
            model.addAttribute("errorMessage", "Bad access. Your request denied");
            return "errors/error";
        }
        if (!validateTour(tour)) {
            model.addAttribute("errorMessage", "Bad input info");
            return "errors/error";
        }
        tour.setId(Integer.parseInt(request.getParameter("id")));
        Tour updatedTour = tourService.updateTour(tour);
        if (updatedTour == null) {
            model.addAttribute("errorMessage", "Error when we try to update tour");
            return "errors/error";
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
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "THERE IS NULL");
            return modelAndView;
        }
        modelAndView.setViewName("tour/showAllTours");
        modelAndView.addObject("tourList", tourList);
        return modelAndView;
    }

    private boolean checkAccess(HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                return SessionManager.getUserIdByCookie(cookie) != 3;
            }
        }
        return false;
    }

    private boolean validateTour(Tour tour) {
        return tour.getAgentId() != 0 && tour.getDuration() != 0
                && tour.getId() != 0 && !(tour.getPrice() == 0) && tour.getTourTypesId() != 0;
    }

    private ModelAndView accessDeniedView() {
        return new ModelAndView("errors/error","errorMessage",
                "Bad access. Your request denied");
    }
}

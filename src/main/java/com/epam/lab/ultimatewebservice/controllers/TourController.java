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
        String isHot = request.getParameter("hot");
        String isActive = request.getParameter("active");
        if (!checkAccess(request)) {
            model.addAttribute("errorMessage", "Bad access. Your request denied");
            return "errors/error";
        }
        if (!checkValidation(tour) || isHot == null || isActive == null) {
            model.addAttribute("errorMessage", "Bad input info");
            return "errors/error";
        }
        Tour createdTour = tourService.createTour(tour);
        if (createdTour == null) {
            model.addAttribute("errorMessage", "Error when we try to create tour");
            return "errors/error";
        }
        model.addAttribute("tour", createdTour);
        model.addAttribute("message", "User successfully created!");
        return "tour/showTour";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteTour(@ModelAttribute Tour tour, Model model,
                             HttpServletRequest request) {
        tour.setId(Integer.parseInt(request.getParameter("id")));
        Tour deletedTour = tourService.getTourById(Integer.parseInt(request.getParameter("id")));
        if (tourService.deleteTourById(tour.getId())) {
            model.addAttribute("message", "Tour successfully Deleted!");
            model.addAttribute("tour", deletedTour);
            return "tour/showTour";
        } else {
            model.addAttribute("errorMessage", "Error when we try to delete tour");
            return "tour/error";

        }
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
        String isHot = request.getParameter("hot");
        String isActive = request.getParameter("active");
        if (updatedTour == null) {
            model.addAttribute("errorMessage", "Error when we try to update tour");
            return "errors/error";
        }
        if ( isActive == null || isHot == null) {
            model.addAttribute("errorMessage","please fill all fields");
            return "errors/error";
        }
        model.addAttribute("message", "Tour successfully Updated!");
        model.addAttribute("tour", updatedTour);
        return "tour/showTour";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllTours(HttpServletRequest request) {

        List<Tour> tourList;
        ModelAndView modelAndView = new ModelAndView();
        tourList = tourService.getTourList();
            if (tourList == null) {
                modelAndView.setViewName("errors/error");
                modelAndView.addObject("errorMessage", "THERE IS NULL");
                return modelAndView;
            }

        modelAndView.setViewName("tour/showAllTours");
        modelAndView.addObject("tourList", tourList);

        return modelAndView;
    }

    @RequestMapping(value = "/agentTours", method = RequestMethod.GET)
    public ModelAndView getAllAgentTours(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        int userId = 0;
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("userLoggedIn")){
                userId = SessionManager.getUserIdByCookie(cookie);
            }
        }

        List<Tour> tourList = tourService.getToursIdByAgentId(userId);
        ModelAndView modelAndView = new ModelAndView();

        if (tourList.size() == 0) {
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
    private boolean checkValidation(Tour tour) {
        return tour.getPrice() != 0  && tour.getDuration() != 0 &&
                tour.getAgentId() != 0 && tour.getTourTypesId() != 0;

    private boolean validateTour(Tour tour) {
        return tour.getAgentId() != 0 && tour.getDuration() != 0
                && tour.getId() != 0 && !(tour.getPrice() == 0) && tour.getTourTypesId() != 0;
    }

    private ModelAndView accessDeniedView() {
        return new ModelAndView("errors/error","errorMessage",
                "Bad access. Your request denied");
    }
}

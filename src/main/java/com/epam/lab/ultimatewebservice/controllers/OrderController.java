package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;
    private static final String LOGGED_COOKIE = "userLoggedIn";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getTourById(@PathVariable(value = "id") int id, HttpServletRequest request) {
        return null;
    }



}

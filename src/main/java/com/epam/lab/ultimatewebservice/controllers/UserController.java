package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") int id) {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserById(id);
        if (!checkUser(user)) {
            throw new RuntimeException("get User by Id exception");
        }
        model.setViewName("index");
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public String updateUser(@RequestBody String params) {
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void createUser(@ModelAttribute("user") User user) {
        if (checkUser(userService.createUser(user))) {
            throw new RuntimeException("Everything is bad");
        }
        //TODO
        //add everything to a model map and redirect it to view-page
    }

    private boolean checkUser(User user) {
        return user.getId() != 0;
    }

}

package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") int id) {
        return checkUserAndReturnModel(userService.getUserById(id));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView createUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String creationOfUser(@ModelAttribute("user") User user, Model model,
                                 HttpServletRequest request) {
        User createdUser = userService.createUser(user);
        String permission = request.getParameter("permission");
        if (createdUser == null || permission == null) {
            model.addAttribute("errorMessage", "Error when we try to create user");
            return "error";
        }
        userService.addPermission(permission);
        model.addAttribute("user", createdUser);
        return "showUser";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public String deleteUserById(@PathVariable String id) {
        return null;
    }

    /*@RequestMapping(value = "/users", method = RequestMethod.PUT)
    public String updateUser(@RequestBody String params) {
        return null;
    }*/

    private ModelAndView checkUserAndReturnModel(User user) {
        ModelAndView model = new ModelAndView();
        if (user == null) {
            model.setViewName("error");
            model.addObject("errorMessage", "User dont exists");
            return model;
        }
        model.setViewName("showUser");
        model.addObject("user", user);
        return model;
    }



}

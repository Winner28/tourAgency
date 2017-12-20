package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") int id) {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserById(id);
        if (!checkUser(user)) {
            model.setViewName("getUserError");
            model.addObject("user", new User().setId(id));
            return model;
        }
        model.setViewName("showUser");
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView createUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void creationOfUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        if (checkUser(userService.createUser(user))) {
            throw new RuntimeException("Everything is bad");
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public String updateUser(@RequestBody String params) {
        return null;
    }



    private boolean checkUser(User user) {
       return user!=null;

    }

}

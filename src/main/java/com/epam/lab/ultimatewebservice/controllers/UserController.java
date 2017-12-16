package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/get/{user_id}", method = RequestMethod.GET)
    public ModelAndView modelAndView(@PathVariable(value = "user_id") int user_id) {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserById(user_id);
        System.out.println(user.getEmail());
       /* if (user.getEmail() == null) {
            model.setViewName("error");
            return model;
        }*/
        model.setViewName("user");
        model.addObject("user", user);
        return model;
    }
}

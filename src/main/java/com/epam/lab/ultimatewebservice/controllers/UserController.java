package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    
    @RequestMapping(value = "/users/get/{user_id}", method = RequestMethod.GET)
    public ModelAndView modelAndView(@PathVariable(value = "user_id") int user_id) {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserById(user_id);
        model.setViewName("user");
        model.addObject("user", user);
        return model;
    }


    private boolean validateUser(User user) {
        return true;
    }

}

package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.exceptions.UserNotFoundException;
import com.epam.lab.ultimatewebservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") int id) {
        ModelAndView model = new ModelAndView();
   /*     User user = userService.getUserById(user_id);
        model.addObject("user", user);*/
        return model;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView createUser( @RequestBody String user) {
        return null;
    }


    @RequestMapping(value = "/users/{id}")
    public ModelAndView deleteUser() {
        return null;
    }

    private void validateUser(String id) {
        if(false)
            throw new UserNotFoundException("exception");
    }

}

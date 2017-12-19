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
        System.out.println(user);
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
    public String createUser(@ModelAttribute("user") User user,
                             ModelMap model) {

        userService.createUser(user);

/*

        model.addAttribute("name", student.getName());
        model.addAttribute("age", student.getAge());
        model.addAttribute("id", student.getId());
*/

        return "result";
    }


}

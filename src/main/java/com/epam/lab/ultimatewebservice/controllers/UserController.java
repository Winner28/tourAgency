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
import java.util.List;


@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    
    @RequestMapping(value = "/users/id/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") int id) {
        return checkUserAndReturnModel(userService.getUserById(id));
    }

    @RequestMapping(value = "/users/email/{email}", method = RequestMethod.GET)
    public ModelAndView getUserByEmail(@PathVariable(value = "email") String email) {
        return checkUserAndReturnModel(userService.getUserByEmail(email));
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.GET)
    public ModelAndView createUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/createUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String creationOfUser(@ModelAttribute("user") User user, Model model,
                                 HttpServletRequest request) {
        User createdUser = userService.createUser(user);
        String permission = request.getParameter("permission");
        if (createdUser == null || permission == null) {
            System.out.println(createdUser);
            model.addAttribute("errorMessage", "Error when we try to create user");
            return "user/error";
        }
        userService.addPermission(permission);
        model.addAttribute("user", createdUser);
        model.addAttribute("message", "User successfully created!");
        return "user/showUser";
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteUserById(@PathVariable String id) {
        User userToDelete = userService.getUserById(Integer.parseInt(id));
        if (userToDelete == null) {
            return new ModelAndView("user/error", "errorMessage",
                    "User with such id dont exists");
        }
        if (!userService.deleteUserById(Integer.parseInt(id))) {
            return new ModelAndView("user/error", "errorMessage",
                    "Smth goes wrong when we trying to delete user");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/showUser");
        modelAndView.addObject("user", userToDelete);
        modelAndView.addObject("message", "User with ID = " +
                userToDelete.getId() + " successfully deleted!");
        return modelAndView;
    }

    @RequestMapping(value = "/users/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateUserPage(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserById(Integer.parseInt(id));
        if (user == null) {
            modelAndView.setViewName("user/error");
            modelAndView.addObject("errorMessage", "We dont have user with such id");
            return modelAndView;
        }
        modelAndView.setViewName("user/updateUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user, Model model,
                             HttpServletRequest request) {
        user.setId(Integer.parseInt(request.getParameter("id")));
        User updatedUser = userService.updateUser(user);
        String permission = request.getParameter("permission");
        if (updatedUser == null) {
            model.addAttribute("errorMessage", "Error when we try to update user");
            return "user/error";
        }
        if (permission != null) {
            userService.updateUserPermission(user.getId(), permission);
        }
        model.addAttribute("message", "User successfully Updated!");
        model.addAttribute("user", updatedUser);
        return "user/showUser";
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> usersList = userService.getUsersList();
        if (usersList == null) {
            modelAndView.setViewName("user/error");
            modelAndView.addObject("errorMessage", "We got null");
            return modelAndView;
        }
        modelAndView.setViewName("user/showAllUsers");
        modelAndView.addObject("userList", usersList);
        return modelAndView;
    }

    private ModelAndView checkUserAndReturnModel(User user) {
        ModelAndView model = new ModelAndView();
        if (user == null) {
            model.setViewName("user/error");
            model.addObject("errorMessage", "User dont exists");
            return model;
        }
        model.setViewName("user/showUser");
        model.addObject("user", user);
        return model;
    }

}

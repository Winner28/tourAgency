package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.Permission;
import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private static final String LOGGED_COOKIE = "userLoggedIn";


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") int id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        return checkUserAndReturnModel(userService.getUserById(id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createUserPage(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/createUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") User user, Model model,
                                 HttpServletRequest request) {
        User createdUser = userService.createUser(user);
        String permission = request.getParameter("permission");
        if (createdUser == null || permission == null) {
            System.out.println(createdUser);
            model.addAttribute("errorMessage", "Error when we try to create user");
            return "user/error";
        }
        userService.createPermission(new Permission().setUserId(createdUser.getId()).
                setPermissionNameId(Integer.parseInt(permission)));
        model.addAttribute("user", createdUser);
        model.addAttribute("message", "User successfully created!");
        return "user/showUser";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
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

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateUserPage(@PathVariable String id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
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
            userService.updatePermission(new Permission().setUserId(updatedUser.getId())
                    .setPermissionNameId(Integer.parseInt(permission)));
        }
        model.addAttribute("message", "User successfully Updated!");
        model.addAttribute("user", updatedUser);
        return "user/showUser";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllUsers(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
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

    @RequestMapping(value = "/permissions/create", method = RequestMethod.GET)
    public ModelAndView createPermissionPage(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/createPermission");
        modelAndView.addObject("permission", new Permission());
        return modelAndView;
    }

    @RequestMapping(value = "/permissions/create" ,method = RequestMethod.POST)
    public String createPermission(@ModelAttribute Permission permission, Model model) {
        if(!userService.createPermission(permission)) {
            model.addAttribute("errorMessage", "Error when creating permission");
            return "user/error";
        }
        model.addAttribute("message", "Successfully created!");
        return "user/info";
    }

    @RequestMapping(value = "/permissions/delete/{user_id}", method = RequestMethod.POST)
    public String deletePermission(@PathVariable int user_id, Model model) {
        if (!userService.deletePermission(user_id)) {
            model.addAttribute("errorMessage", "Error when try to delete user Permission");
            return "user/error";
        }
        model.addAttribute("message", "Successfully deleted!");
        return "user/info";
    }

    @RequestMapping(value = "/permissions/update/{user_id}", method = RequestMethod.GET)
    public ModelAndView updatePermissionPage(@PathVariable int user_id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/updatePermission");
        modelAndView.addObject("permission", new Permission().setUserId(user_id));
        return modelAndView;
    }

    @RequestMapping(value = "/permissions/update", method = RequestMethod.POST)
    public String updatePermissionPage(@ModelAttribute Permission permission, Model model) {
       if(!userService.updatePermission(permission)) {
           model.addAttribute("errorMessage", "Error when try to update user Permission");
           return "user/error";
       }

        model.addAttribute("message", "Successfully updated!");
        return "user/info";
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

    private boolean checkAccess(HttpServletRequest request) {
        Cookie []cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                if (id == 0)
                    return false;
                int permission_id = userService.getPermission(id);
                return permission_id == 1;
            }
        }

        return false;
    }

    private ModelAndView accessDeniedView() {
        return new ModelAndView("user/error","errorMessage",
                "Bad access. Your request denied");
    }
}

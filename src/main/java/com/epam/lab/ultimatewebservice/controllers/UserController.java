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
        String permission = request.getParameter("permission");
        if (!checkValidation(user) || permission == null) {
            model.addAttribute("errorMessage", "Error when we try to create user, some fields are empty");
            return "errors/error";
        }
        User createdUser = userService.createUser(user);
        if (createdUser == null) {
            model.addAttribute("errorMessage", "Error when we try to create user");
            return "errors/error";
        }
        userService.createPermission(new Permission().setUserId(createdUser.getId()).
                setPermissionNameId(Integer.parseInt(permission)));
        model.addAttribute("user", createdUser);
        model.addAttribute("message", "User successfully created!");
        return "user/showUser";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteUserById(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id.isEmpty() || Integer.parseInt(id) < 0) {
            return new ModelAndView("errors/error", "errorMessage",
                    "Bad input");
        }
        if(!userService.deletePermission(Integer.parseInt(id))) {
            return new ModelAndView("errors/error", "errorMessage",
                    "Cant delete permission that already deleted");
        }
        User userToDelete = userService.getUserById(Integer.parseInt(id));
        if (userToDelete == null) {
            return new ModelAndView("errors/error", "errorMessage",
                    "User with such id dont exists");
        }
        if (!userService.deleteUserById(Integer.parseInt(id))) {
            return new ModelAndView("errors/error", "errorMessage",
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
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "We dont have user with such id");
            return modelAndView;
        }
        modelAndView.setViewName("user/updateUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateUserPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "We dont have user with such id");
            return modelAndView;
        }
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        User user = userService.getUserById(Integer.parseInt(id));
        if (user == null) {
            modelAndView.setViewName("errors/error");
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
            return "errors/error";
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
            modelAndView.setViewName("errors/error");
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
            return "errors/error";
        }
        model.addAttribute("message", "Successfully created!");
        return "user/info";
    }

    @RequestMapping(value = "/permissions/delete/{user_id}", method = RequestMethod.POST)
    public String deletePermission(@PathVariable int user_id, Model model) {
        if (!userService.deletePermission(user_id)) {
            model.addAttribute("errorMessage", "Error when try to delete user Permission");
            return "errors/error";
        }
        model.addAttribute("message", "Successfully deleted!");
        return "user/info";
    }

    @RequestMapping(value = "/permissions/update", method = RequestMethod.GET)
    public ModelAndView updatePermissionPage(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/updatePermission");
        modelAndView.addObject("permission", new Permission());
        return modelAndView;
    }

    @RequestMapping(value = "/permissions/get", method = RequestMethod.POST)
    public ModelAndView getPermissionByUserId(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            modelAndView.addObject("errorMessage", "Bad input info");
            modelAndView.setViewName("errors/error");
            return modelAndView;
        }
        int permissionId = userService.getPermission(Integer.parseInt(id));
        if (permissionId == 0) {
            modelAndView.addObject("errorMessage", "Error when try to get user Permission");
            modelAndView.setViewName("errors/error");
            return modelAndView;
        }
        modelAndView.setViewName("user/showPermission");
        modelAndView.addObject("permission", new Permission().
                setUserId(Integer.parseInt(id)).setPermissionNameId(permissionId));
        return modelAndView;
    }

    @RequestMapping(value = "/permissions/update", method = RequestMethod.POST)
    public String updatePermissionPage(@ModelAttribute Permission permission, Model model) {
        if (permission.getUserId() == 0 || permission.getPermissionNameId() == 0 || permission.getPermissionNameId() > 3) {
            model.addAttribute("errorMessage", "Bad input");
            return "errors/error";
        }
        if(!userService.updatePermission(permission)) {
            model.addAttribute("errorMessage", "Error when try to update user Permission");
            return "errors/error";
        }
        model.addAttribute("message", "Successfully updated!");
        return "user/info";
    }

    @RequestMapping(value = "/permissions/id", method = RequestMethod.POST)
    public ModelAndView getPermissionsByPermissionId(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        String id = request.getParameter("id");
        ModelAndView modelAndView = new ModelAndView();
        if (id == null || id.isEmpty()) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "Bad input info");
            return modelAndView;
        }
        List<Integer> usersId = userService.getPermissionListById(Integer.parseInt(id));
        if (usersId.size() == 0 || usersId == null) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "Something goes wrong");
            return modelAndView;
        }
        modelAndView.addObject("users_id", usersId);
        switch (Integer.parseInt(id)) {
            case 1:
                modelAndView.addObject("userType", "Admins");
                break;
            case 2:
                modelAndView.addObject("userType", "Agents");
                break;
            case 3:
                modelAndView.addObject("userType", "Clients");
                break;
            default:
                modelAndView.addObject("userType", "Clients");
                break;
        }

        modelAndView.setViewName("user/permissionsIdList");
        return modelAndView;
    }

    @RequestMapping(value = "/permissions/all", method = RequestMethod.GET)
    public ModelAndView getPermissionList(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        List<Permission> permissionList =userService.getAllPermissions();
        if (permissionList.size() == 0 || permissionList == null) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "Something goes wrong");
            return modelAndView;
        }
        modelAndView.setViewName("user/permissionsList");
        modelAndView.addObject("permissions", permissionList);
        return modelAndView;
    }

    private ModelAndView checkUserAndReturnModel(User user) {
        ModelAndView model = new ModelAndView();
        if (user == null) {
            model.setViewName("errors/error");
            model.addObject("errorMessage", "User dont exists");
            return model;
        }
        model.setViewName("user/showUser");
        model.addObject("user", user);
        return model;
    }

    private boolean checkAccess(HttpServletRequest request) {
        Cookie []cookies = request.getCookies();
        if (cookies == null) return false;
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
        return new ModelAndView("errors/error","errorMessage",
                "Bad access. Your request denied");
    }

    private boolean checkValidation(User user) {
        return !user.getFirstName().isEmpty() && !user.getLastName().isEmpty() &&
                !user.getPasswordHash().isEmpty() && !user.getEmail().isEmpty();
    }
}
package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final RegistrationService registrationService;

    private static final String LOGGED_COOKIE = "userLoggedIn";
    private final static String EMAIL= "email";
    private final static String FIRSTNAME = "firstName";
    private final static String LASTNAME  = "lastName";
    private final static String PASSWORD  = "passwordHash";

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage(HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                if (id == 0) {
                    return "authorization/registration";
                }
                return "redirect:/home";
            }
        }
        return "authorization/registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request, Model model) {
        User user = new User()
                .setFirstName(request.getParameter(FIRSTNAME))
                .setLastName(request.getParameter(LASTNAME))
                .setEmail(request.getParameter(EMAIL))
                .setPasswordHash(request.getParameter(PASSWORD));
        if (!userValidation(user)) {
            model.addAttribute("errorMessage", "Can`t register, enter all necessary information please");
            return "authorization/registration";
        }
        if (!registrationService.registerUser(user)){
            model.addAttribute("erorMessage", "Failed to register user (Server error)");
            return "user/error";
        }
        return "redirect:/home";
    }

    private boolean userValidation(User user) {
        return !user.getFirstName().isEmpty() && !user.getLastName().isEmpty() &&
                !user.getPasswordHash().isEmpty() && !user.getEmail().isEmpty();
    }

}

package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationController {

    private final AuthorizationService service;
    private static final String LOGGED_COOKIE = "userLoggedIn";


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                String id = SessionManager.checkIfUserLogined(cookie);
                if (id == null) {
                    return "authorization/login";
                }
                return "redirect:/home";
            }
        }
        return "authorization/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginAction(Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (!checkArgs(email, password)) {
            model.addAttribute("error", "Fields can`t be empty");
            return "authorization/login";
        }
        User user = service.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Your username or password is invalid.");
            return "authorization/login";
        }
        Cookie userCookie = new Cookie(LOGGED_COOKIE, generateHash(user));
        response.addCookie(userCookie);
        SessionManager.createNewSession(userCookie, String.valueOf(user.getId()));
        return "redirect:/home";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String userPage(HttpServletRequest request, Model model) {
        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                String value = SessionManager.checkIfUserLogined(cookie);
                if (value == null) {
                    return "redirect:/login";
                }
                int id = Integer.valueOf(value);
                User user = service.getUserById(id);
                if (user == null) {
                    model.addAttribute("errorMessage", "Smth goes wrong");
                }
                model.addAttribute("userPage");
                model.addAttribute("user", user);
                return "userPage";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request) {
        Cookie []cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                SessionManager.userLogOut(cookie);
                break;
            }
        }
        return "redirect:/login";
    }

    private boolean checkArgs(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    @SneakyThrows
    public String generateHash(User user) {
        return user.toString();
    }
}

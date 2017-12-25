package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private static final String LOGGED_COOKIE = "userLoggedIn";


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();
        if (cookies == null) {
            return "authorization/login";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                if (id == 0) {
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
        User user = authorizationService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Your username or password is invalid.");
            return "authorization/login";
        }
        Cookie userCookie = new Cookie(LOGGED_COOKIE, generateHash(user));
        response.addCookie(userCookie);
        SessionManager.createNewSession(userCookie, user.getId());
        return "redirect:/home";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String userPage(HttpServletRequest request, Model model) {
        Cookie [] cookies = request.getCookies();
        if(cookies == null){
            return "redirect:/login";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                if (id == 0) {
                    return "redirect:/login";
                }
                User user = authorizationService.getUserById(id);
                if (user == null) {
                    model.addAttribute("errorMessage", "Smth goes wrong");
                }
                model.addAttribute("userPage");
                model.addAttribute("user", user);

                switch (authorizationService.getUserPermission(id)) {
                    case 1:
                        model.addAttribute("userType", "admin");
                        break;
                    case 2:
                        model.addAttribute("userType", "agent");
                        break;
                    case 3:
                        model.addAttribute("userType", "client");
                        break;
                    default:
                        model.addAttribute("userType", "client");
                        break;
                }

                return "userPage";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
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


    private String generateHash(User user) {
        return user.toString();
    }
}

package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.User;
import com.epam.lab.ultimatewebservice.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationController {

    private final AuthorizationService service;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "authorization/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginAction(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (!checkArgs(email, password)) {
            model.addAttribute("error", "Fields can`t be empty");
            return "authorization/login";
        }
        if (!service.login(email, password)) {
            model.addAttribute("error", "Your username or password is invalid.");
            return "authorization/login";
        }

        //TODO
        //sessionManager.createSession(email, password, request);
        return "redirect:/home";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String userPage(HttpServletRequest request) {
        //???
       /*
        User user = service.getUserByEmail((String) request.getAttribute("email"));
        if (user == null) {
            model.addAttribute("user", request.getAttribute("email"));
            return "userPage";
        }
        model.addAttribute("user", user);*/
        return "userPage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(User user, HttpServletRequest request) {
        return "redirect:/login";
    }

    private boolean checkArgs(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}

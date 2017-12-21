package com.epam.lab.ultimatewebservice.controllers;


import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static Map<String, String> sessionMap = new ConcurrentHashMap<>();
    private static String cookieName = "userLoggedIn";

    public static void createNewSession(String id, Cookie cookie) {
        if (cookie.getName().equals(cookieName)) {
            sessionMap.put(cookie.getValue(), id);
        }
    }

    public static String checkIfUserLogined(Cookie cookie) {
        if (cookie.getName().equals(cookieName)) {
            if (sessionMap.get(cookie.getValue()) != null) {
                return sessionMap.get(cookie.getValue());
            } else {
                return null;
            }
        }
        return null;
    }
}

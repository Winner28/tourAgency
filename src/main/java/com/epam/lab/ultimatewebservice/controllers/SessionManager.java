package com.epam.lab.ultimatewebservice.controllers;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static final Map<String, String> SESSIONS = new ConcurrentHashMap<>();
    private static final String COOKIE_NAME = "userLoggedIn";

    public static void createNewSession(Cookie cookie, String id) {
        if (cookie.getName().equals(COOKIE_NAME)) {
            SESSIONS.put(cookie.getValue(), id);
        }
    }

    public static String checkIfUserLogined(Cookie cookie) {
        if (cookie.getName().equals(COOKIE_NAME) && SESSIONS.get(cookie.getValue()) != null) {
                return SESSIONS.get(cookie.getValue());
        }
        return null;
    }

    public static void userLogOut(Cookie cookie) {
        SESSIONS.remove(cookie.getValue());
    }
}

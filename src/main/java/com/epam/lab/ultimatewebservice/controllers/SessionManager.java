package com.epam.lab.ultimatewebservice.controllers;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static final Map<String, Integer> SESSIONS = new ConcurrentHashMap<>();
    private static final String COOKIE_NAME = "userLoggedIn";

    public static void createNewSession(Cookie cookie, int id) {
        if (cookie.getName().equals(COOKIE_NAME)) {
            SESSIONS.put(cookie.getValue(), id);
        }
    }

    public static int checkIfUserLogined(Cookie cookie) {
        if (cookie.getName().equals(COOKIE_NAME) && SESSIONS.get(cookie.getValue()) != null) {
                return SESSIONS.get(cookie.getValue());
        }
        return 0;
    }

    public static void userLogOut(Cookie cookie) {
        SESSIONS.remove(cookie.getValue());
    }
}

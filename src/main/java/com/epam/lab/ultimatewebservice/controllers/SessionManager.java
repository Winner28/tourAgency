package com.epam.lab.ultimatewebservice.controllers;


import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionManager {

    /*private static Map<Integer, String> sessionMap = new HashMap<>();

    public static void createNewSession(User user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();
        sessionMap.put(user.getId(), sessionId);
    }

    public static boolean checkIfUserLogined(User user) {
        return sessionMap.get(user.getId()) != null;
    }
    */
}

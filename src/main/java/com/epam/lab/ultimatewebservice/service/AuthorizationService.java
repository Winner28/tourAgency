package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import com.epam.lab.ultimatewebservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationService {

    private final UserService userService;

    public User login(String email, String password) {
        User user = userService.getUserByEmail(email);
        if (user == null || (!user.getEmail().equals(email.trim())
                && user.getPasswordHash().equals(password.trim()))) {
            return null;
        }
        return user;
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

}

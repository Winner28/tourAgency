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

    private UserDAO userDAO;

    public boolean login(String email, String password) {
        Optional<User> optionalUser = userDAO.getUserByEmail(email);
        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        return user.getEmail().equals(email.trim()) && user.getPasswordHash().equals(password.trim());
    }

    public boolean logout() {
        return false;
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email).orElse(null);
    }
}

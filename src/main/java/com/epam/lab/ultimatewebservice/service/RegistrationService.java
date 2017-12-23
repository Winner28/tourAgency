package com.epam.lab.ultimatewebservice.service;


import com.epam.lab.ultimatewebservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationService {

    private final UserService userService;

    public boolean registerUser(User user) {
        return userService.createUser(user) != null;
    }

}

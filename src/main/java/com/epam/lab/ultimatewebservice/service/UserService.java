package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(int id) {
        if (userDAO.getUserById(id).isPresent()) {
            return userDAO.getUserById(id).get();
        }
        return new User();
    }

}

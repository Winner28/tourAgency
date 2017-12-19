package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import com.epam.lab.ultimatewebservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private  UserDAO userDAO;

    public User getUserById(int id) {
        if (userDAO.getUserById(id).isPresent()) {
            return userDAO.getUserById(id).get();
        }
        return new User();
    }

    public User createUser(User user) {
       if (userDAO.addUser(user).isPresent()) {
           return userDAO.addUser(user).get();
       }
       return null;
    }
}

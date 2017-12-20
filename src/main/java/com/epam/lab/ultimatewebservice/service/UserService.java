package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import com.epam.lab.ultimatewebservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private  UserDAO userDAO;

    public User getUserById(int id) {
        return userDAO.getUserById(id).orElse(null);
    }

    public User createUser(User user) {
       return userDAO.addUser(user).orElse(null);
    }

    public User updateUser(User user) {
        return userDAO.updateUser(user).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email).orElse(null);
    }

    public List<User> getUsersList() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUserById(int id){
        return userDAO.deleteUserById(id) > 0;
    }
}

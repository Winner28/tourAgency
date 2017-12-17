package com.epam.lab.ultimatewebservice.db.dao;

public class UserDAO {
    private static UserDAO instance;

    private static final String ADD_USER_SCRIPT =
            "INSERT INTO users(first_name, last_name, email, password_hash) VALUES(?,?,?,?)";
    private static final String DELETE_USER_BYID =
            "DELETE FROM users WHERE id=?";
    private static final String GET_ALL_USERS =
            "SELECT (first_name, last_name, email, password_hash) FROM users";
    private static final String GET_USER_BYID =
            "SELECT (first_name, last_name, email, password_hash) FROM users WHERE id=?";
    private static final String GET_USER_BY_EMAIL =
            "SELECT (first_name, last_name, email, password_hash) FROM users WHERE email=?";
    private static final String UPDATE_USER =
            "UPDATE users SET first_name=?, last_name=?, email=?, password_hash=? WHERE id=?";

    private UserDAO() {}

    public static synchronized UserDAO getInstance() {
        if (instance == null)
            instance = new UserDAO();
        return instance;
    }

    public void addUser(){

    }

    public void updateUser(){

    }

    public void deleteUserById(){

    }

    public List<User> getAllUsers() {

    }

    public Optional<User> getUserByID(int id){

    }

    public Optional<User> getUserByEmail(String email){

    }

}

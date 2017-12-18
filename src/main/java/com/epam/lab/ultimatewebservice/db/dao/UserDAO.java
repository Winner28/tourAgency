package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private final String ADD_USER =
            "INSERT INTO users(first_name, last_name, email, password_hash) VALUES(?,?,?,?)";
    private final String DELETE_USER_BYID =
            "DELETE FROM users WHERE id=?";
    private final String GET_ALL_USERS =
            "SELECT (first_name, last_name, email, password_hash) FROM users";
    private final String GET_USER_BYID =
            "SELECT (first_name, last_name, email, password_hash) FROM users WHERE id=?";
    private final String GET_USER_BY_EMAIL =
            "SELECT (first_name, last_name, email, password_hash) FROM users WHERE email=?";
    private final String UPDATE_USER =
            "UPDATE users SET first_name=?, last_name=?, email=?, password_hash=? WHERE id=?";

    private JdbcDAO jdbcDAO;

    @Autowired
    public UserDAO(ConnectionPool connectionPool) {
        jdbcDAO = () -> {
            try {
                return connectionPool.getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }



    public void addUser(User user){
        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

            }catch (SQLException e){
                e.printStackTrace();
            }
        }, ADD_USER, User.getFirstName(), User.getLastname(), User.getEmail(), User.getPassword_hash());
    }

    public void updateUser(){

    }

    public void deleteUserById(){

    }

    public List<User> getAllUsers() {
        return null;
    }

    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User()
                            .setId(id)
                            .setFirstname(resultSet.getString("firstname"))
                            .setLastname(resultSet.getString("lastname"))
                            .setEmail(resultSet.getString("email"))
                            .setPassword_hash(resultSet.getString("password_hash"));
                } else {
                    return null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }, GET_USER_BYID, id));
    }


    public Optional<User> getUserByEmail(String email){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return new User()
                            .setId(resultSet.getString("id"))
                            .setFirstname(resultSet.getString("firstname"))
                            .setLastname(resultSet.getString("lastname"))
                            .setEmail(resultSet.getString(email))
                            .setPassword_hash(resultSet.getString("password_hash"));
                } else
                    return null;
            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }, GET_USER_BY_EMAIL, email));
    }

}

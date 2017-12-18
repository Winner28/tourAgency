package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private final String ADD_USER =
            "INSERT INTO Users(first_name, last_name, email, password_hash) VALUES(?,?,?,?)";
    private final String DELETE_USER_BY_ID =
            "DELETE FROM Users WHERE id=?";
    private final String GET_ALL_USERS =
            "SELECT (first_name, last_name, email, password_hash) FROM Users";
    private final String GET_USER_BY_ID =
            "SELECT (first_name, last_name, email, password_hash) FROM users WHERE id=?";
    private final String GET_USER_BY_EMAIL =
            "SELECT (first_name, last_name, email, password_hash) FROM Users WHERE email=?";
    private final String UPDATE_USER_BY_ID =
            "UPDATE Users SET first_name=?, last_name=?, email=?, password_hash=? WHERE id=?";

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



    public Optional<User> addUser(User user){
       return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                }
                user.setId(resultSet.getInt("id"));
                return user;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }, ADD_USER, user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword_hash()));
    }

    public Optional<User> updateUser(User user){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            if (!getUserById(user.getId()).isPresent()) {
                return null;
            }
                    try {
                        preparedStatement.executeUpdate();
                        return user;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }, UPDATE_USER_BY_ID, user.getFirstname(),
                user.getLastname(), user.getEmail(), user.getPassword_hash(), user.getId()));
    }

    public boolean deleteUserById(int id){
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }, DELETE_USER_BY_ID, id);

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    userList.add(new User()
                                .setId(rs.getInt("id"))
                                .setFirstname(rs.getString("firstname"))
                                .setLastname(rs.getString("lastname"))
                                .setEmail(rs.getString("email"))
                                .setPassword_hash(rs.getString("password_hash"))
                    );
                }
            }catch (SQLException e) {
                throw new RuntimeException("Got an exception");
            }
        }, GET_ALL_USERS);

        return userList;
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

        }, GET_USER_BY_ID, id));
    }


    public Optional<User> getUserByEmail(String email){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return new User()
                            .setId(resultSet.getInt("id"))
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

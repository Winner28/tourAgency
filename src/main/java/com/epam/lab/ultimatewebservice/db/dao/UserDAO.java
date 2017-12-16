package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class UserDAO {


    private JdbcDAO jdbcDAO;


    @Autowired
    public UserDAO(DataSource dataSource) {
        jdbcDAO = () -> {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("no connection!");
            }
        };
    }

    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(id);
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setPassword_hash(resultSet.getString("password_hash"));
                    return user;
                } else {
                    return null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }, "SELECT * From Users Where id=?", id));
    }

}


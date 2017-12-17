package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class UserDAO {

    private JdbcDAO jdbcDAO;

    @Autowired
    public UserDAO(ConnectionPool connectionPool) {
        jdbcDAO = connectionPool::getConnection;
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

        }, "SELECT id, firstname, lastname, email, password_hash FROM Users WHERE id=?", id));
    }

}


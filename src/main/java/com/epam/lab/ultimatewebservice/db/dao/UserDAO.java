package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDAO {

    private final String ADD_USER =
            "INSERT INTO Users(firstname, lastname, email, password_hash) VALUES(?,?,?,?)";
    private final String DELETE_USER_BY_ID =
            "DELETE FROM Users WHERE id=?";
    private final String GET_ALL_USERS =
            "SELECT (firstname, lastname, email, password_hash) FROM Users";
    private final String GET_USER_BY_ID =
            "SELECT (firstname, lastname, email, password_hash) FROM users WHERE id=?";
    private final String GET_USER_BY_EMAIL =
            "SELECT (firstname, lastname, email, password_hash) FROM Users WHERE email=?";


    private final String id = "id";
    private final String email = "email";
    private final String firstname = "firstname";
    private final String lastname  = "lastname";
    private final String password  = "password_hash";

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
                    user.setId(resultSet.getInt(id));
                }
                user.setId(resultSet.getInt(id));
                return user;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }, ADD_USER, user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword_hash()));
    }

    public Optional<User> updateUser(User user){
        StringBuilder SQL = new StringBuilder("UPDATE Users SET ");
        Map<String, String> userMap = new LinkedHashMap<>();
        if (!getUserById(user.getId()).isPresent()) {
            return Optional.empty();
        }
        User updUser = getUserById(user.getId()).get();
        if (user.getFirstname() != null) {
            userMap.put(firstname, user.getFirstname());
            updUser.setFirstname(user.getFirstname());
        }
        if (user.getLastname() != null) {
            userMap.put(lastname, user.getLastname());
            updUser.setFirstname(user.getLastname());
        }
        if (user.getEmail() != null) {
            userMap.put(email, user.getEmail());
            updUser.setEmail(user.getEmail());
        }
        if (user.getPassword_hash() != null) {
            userMap.put(password, user.getPassword_hash());
            updUser.setPassword_hash(user.getPassword_hash());
        }
        for (Iterator<Map.Entry<String, String>> iterator = userMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> param = iterator.next();
            SQL.append(param.getKey()).append("=?");
            if (iterator.hasNext()) {
                SQL.append(", ");
            } else {
                SQL.append(" ");
            }
        }
        SQL.append(" WHERE id=?");
        jdbcDAO.withPreparedStatement(preparedStatement -> {

        }, SQL.toString(), userMap.values().toArray());

        return Optional.of(updUser);
    }

    public int deleteUserById(int id){
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }, DELETE_USER_BY_ID, id);

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    userList.add(new User()
                                .setId(rs.getInt(id))
                                .setFirstname(rs.getString(firstname))
                                .setLastname(rs.getString(lastname))
                                .setEmail(rs.getString(email))
                                .setPassword_hash(rs.getString(password))
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
                            .setFirstname(resultSet.getString(firstname))
                            .setLastname(resultSet.getString(lastname))
                            .setEmail(resultSet.getString(email))
                            .setPassword_hash(resultSet.getString(password));
                }
                    return null;
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
                            .setId(resultSet.getInt(id))
                            .setFirstname(resultSet.getString(firstname))
                            .setLastname(resultSet.getString(lastname))
                            .setEmail(resultSet.getString(email))
                            .setPassword_hash(resultSet.getString(password));
                }
                    return null;
            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }, GET_USER_BY_EMAIL, email));
    }

}

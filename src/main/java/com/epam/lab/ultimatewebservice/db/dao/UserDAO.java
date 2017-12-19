package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Component
public class UserDAO {

    private final static String ADD_USER =
            "INSERT INTO Users(firstname, lastname, email, password_hash) VALUES(?,?,?,?)";
    private final static String DELETE_USER_BY_ID =
            "DELETE FROM Users WHERE id=?";
    private final static String GET_ALL_USERS =
            "SELECT (firstname, lastname, email, password_hash) FROM Users";
    private final static String GET_USER_BY_ID =
            "SELECT (firstname, lastname, email, password_hash) FROM users WHERE id=?";
    private final static String GET_USER_BY_EMAIL =
            "SELECT (firstname, lastname, email, password_hash) FROM Users WHERE email=?";


    private final static String ID = "id";
    private final static String EMAIL= "email";
    private final static String FIRSTNAME = "firstname";
    private final static String LASTNAME  = "lastname";
    private final static String PASSWORD  = "password_hash";

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

    public Optional<User> addUser(User user) {
       return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(ID));
                }
                user.setId(resultSet.getInt(ID));
                return user;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }, ADD_USER, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPasswordHash()));
    }

    public Optional<User> updateUser(User updatedUser) {
        Optional<User> optionalUser = getUserById(updatedUser.getId());
        if (!optionalUser.isPresent()) {
            return Optional.empty();
        }
        User toUpdate = optionalUser.get();
        Map<String, String> userMap = getFieldsToUpdate(updatedUser, toUpdate);
        StringBuilder SQL = new StringBuilder("UPDATE Users SET ");
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
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, SQL.toString(), userMap.values().toArray());

        return Optional.of(toUpdate);
    }

    public int deleteUserById(int id) {
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
                                .setId(rs.getInt(ID))
                                .setFirstName(rs.getString(FIRSTNAME))
                                .setLastName(rs.getString(LASTNAME))
                                .setEmail(rs.getString(EMAIL))
                                .setPasswordHash(rs.getString(PASSWORD))
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
                            .setFirstName(resultSet.getString(FIRSTNAME))
                            .setLastName(resultSet.getString(LASTNAME))
                            .setEmail(resultSet.getString(EMAIL))
                            .setPasswordHash(resultSet.getString(PASSWORD));
                }
                    return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, GET_USER_BY_ID, id));
    }


    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return new User()
                            .setId(resultSet.getInt(ID))
                            .setFirstName(resultSet.getString(FIRSTNAME))
                            .setLastName(resultSet.getString(LASTNAME))
                            .setEmail(resultSet.getString(email))
                            .setPasswordHash(resultSet.getString(PASSWORD));
                }
                    return null;
            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }, GET_USER_BY_EMAIL, email));
    }

    private Map<String, String> getFieldsToUpdate(User updatedUser, User toUpdate) {
        Map<String, String> userMap = new LinkedHashMap<>();
        if (updatedUser.getFirstName() != null &&
                !toUpdate.getFirstName().equals(updatedUser.getFirstName())) {
            userMap.put(FIRSTNAME, updatedUser.getFirstName());
            toUpdate.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null &&
                !toUpdate.getLastName().equals(updatedUser.getFirstName())) {
            userMap.put(LASTNAME, updatedUser.getLastName());
            toUpdate.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null &&
                !toUpdate.getEmail().equals(updatedUser.getEmail())) {
            userMap.put(EMAIL, updatedUser.getEmail());
            toUpdate.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPasswordHash() != null &&
        !toUpdate.getPasswordHash().equals(updatedUser.getPasswordHash())) {
            userMap.put(PASSWORD, updatedUser.getPasswordHash());
            toUpdate.setPasswordHash(updatedUser.getPasswordHash());
        }

        return userMap;
    }

}

package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Component
public class UserDAO {

    private final static String ADD_USER =
            "INSERT INTO users (first_name, last_name, email, password_hash) VALUES(?,?,?,?)";
    private final static String DELETE_USER_BY_ID =
            "DELETE FROM users WHERE id=?";
    private final static String GET_ALL_USERS =
            "SELECT id,first_name, last_name, email, password_hash FROM users";
    private final static String GET_USER_BY_ID =
            "SELECT id,first_name, last_name, email, password_hash FROM users WHERE id=?";
    private final static String GET_USER_BY_EMAIL =
            "SELECT id,first_name, last_name, email, password_hash FROM users WHERE email=?";


    private final static String ID = "id";
    private final static String EMAIL= "email";
    private final static String FIRSTNAME = "first_name";
    private final static String LASTNAME  = "last_name";
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
        return Optional.ofNullable(jdbcDAO.mapPreparedStatementFlagged(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                return !rs.next() ? null : new User()
                        .setId(rs.getInt(1))
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setEmail(user.getEmail())
                        .setPasswordHash(user.getPasswordHash());
            } catch (SQLException e) {
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
        if (userMap.size() == 0) {
            return Optional.ofNullable(toUpdate);
        }
        StringBuilder SQL = new StringBuilder("UPDATE users SET ");
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
        System.out.println("AND SQL STRING: " + SQL);
        userMap.put("id", String.valueOf(updatedUser.getId()));
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return Optional.of(toUpdate);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, SQL.toString(), userMap.values().toArray());

    }

    public boolean deleteUserById(int id) {
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
                if(resultSet.next()) {
                    return new User()
                            .setId(resultSet.getInt(ID))
                            .setFirstName(resultSet.getString(FIRSTNAME))
                            .setLastName(resultSet.getString(LASTNAME))
                            .setEmail(resultSet.getString(EMAIL))
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
                !toUpdate.getFirstName().equals(updatedUser.getFirstName().trim())
                && !updatedUser.getFirstName().isEmpty()) {
            userMap.put(FIRSTNAME, updatedUser.getFirstName());
            toUpdate.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null &&
                !toUpdate.getLastName().equals(updatedUser.getLastName().trim())
                && !updatedUser.getLastName().isEmpty()) {
            userMap.put(LASTNAME, updatedUser.getLastName());
            toUpdate.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null &&
                !toUpdate.getEmail().equals(updatedUser.getEmail().trim())
                && !updatedUser.getEmail().isEmpty()) {
            userMap.put(EMAIL, updatedUser.getEmail());
            toUpdate.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPasswordHash() != null &&
                !toUpdate.getPasswordHash().equals(updatedUser.getPasswordHash().trim())
                && !updatedUser.getPasswordHash().isEmpty()) {
            userMap.put(PASSWORD, updatedUser.getPasswordHash());
            toUpdate.setPasswordHash(updatedUser.getPasswordHash());
        }
        return userMap;
    }

}

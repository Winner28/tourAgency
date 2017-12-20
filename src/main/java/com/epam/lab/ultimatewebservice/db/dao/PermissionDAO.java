package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionDAO {

    private final static String ADD_USER_PERMISSION =
            "INSERT INTO Permissions(userId, permissionNameId) VALUES(?,?)";
    private final static String DELETE_USER_PERMISSION =
            "DELETE FROM Permissions WHERE userId=?";
/*    private final static String GET_ALL_USERS_ID_BY_PERMISSION_NAME =
            "SELECT (firstname, lastname, email, password_hash) FROM Users";
    private final static String GET_ALL_USERS =
            "SELECT (firstname, lastname, email, password_hash) FROM Users";*/

    private final static String USER_ID = "userId";
    private final static String PERMISSION_NAME_ID= "permissionNameId";


    private JdbcDAO jdbcDAO;

    @Autowired
    public PermissionDAO(ConnectionPool connectionPool) {
        jdbcDAO = () -> {
            try {
                return connectionPool.getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}

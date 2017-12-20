package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PermissionDAO {

    private final static String ADD_USER_PERMISSION =
            "INSERT INTO Permissions(userId, permissionNameId) VALUES(?,?)";
    private final static String DELETE_USER_PERMISSION =
            "DELETE FROM Permissions WHERE userId=?";
    private final static String GET_USER_PERMISSION_BY_ID =
            "SELECT p.userId, p.permissionNameId, n.id, n.name FROM Permissions p " +
                    "INNER JOIN PermissionNames n ON p.permissionNameId=n.id " +
                    "WHERE p.userId=?";
    private final static String GET_ALL_USERS_ID_BY_PERMISSION_NAME_ID =
            "SELECT userId, permissionNameId FROM Permissions WHERE permissionNameId=?";
    private final static String GET_ALL_PERMISSIONS =
            "SELECT userId, permissionNameId FROM Permissions";
    private final static String UPDATE_USER_PERMISSION =
            "UPDATE Permissions SET permissionNameId WHERE userId=?";

    private final static String ID = "userId";
    private final static String PERMISSION = "permissionNameId";


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

    public String getUserPermission(int id) {
       /*return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
           try(ResultSet resultSet = preparedStatement.executeQuery()) {
               if (resultSet.next()) {
                   return new Permission()
                           .setUserId(resultSet.getInt(ID))
                           .setPermissionNameId(resultSet.getInt(PERMISSION));
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return null;
           }
           return null;
        }, GET_USER_PERMISSION_BY_ID, id));*/

       return jdbcDAO.mapPreparedStatement(preparedStatement -> {
           try(ResultSet rs = preparedStatement.executeQuery()) {
               if (rs.next()) {
                   return rs.getString("name");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return null;
       },GET_USER_PERMISSION_BY_ID, id);

    }

    public Optional<Permission> addUserPermission(Permission permission) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return permission;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, ADD_USER_PERMISSION, permission.getUserId(), permission.getPermissionNameId()));
    }

    public int deleteUserPermission(int id) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }, DELETE_USER_PERMISSION, id);
    }

    public int updatePermission(int id) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        },UPDATE_USER_PERMISSION, id);
    }

    public List<Integer> getUsersIdByPermissionNameId(int permissionId) {
        List<Integer> usersId = new ArrayList<>();
        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    usersId.add(rs.getInt(ID));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, GET_ALL_USERS_ID_BY_PERMISSION_NAME_ID, permissionId);
        return usersId;
    }

    public List<Permission> getPermissionsList() {
        List<Permission> permissionList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    permissionList.add(new Permission()
                            .setUserId(rs.getInt(ID))
                            .setPermissionNameId(rs.getInt(PERMISSION)));
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        },GET_ALL_PERMISSIONS);
        return permissionList;
    }
}

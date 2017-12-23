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
            "INSERT INTO permissions(user_id, permission_name_id) VALUES(?,?)";
    private final static String DELETE_USER_PERMISSION =
            "DELETE FROM permissions WHERE user_id=?";
    private final static String GET_USER_PERMISSION_BY_ID =
            "SELECT user_id, permission_name_id FROM permissions WHERE user_id=?";
    private final static String GET_ALL_USERS_ID_BY_PERMISSION_NAME_ID =
            "SELECT user_id, permission_name_id FROM permissions WHERE permission_name_id=?";
    private final static String GET_ALL_PERMISSIONS =
            "SELECT user_id, permission_name_id FROM permissions";
    private final static String UPDATE_USER_PERMISSION =
            "UPDATE permissions SET permission_name_id=? WHERE user_id=?";
    private final static String GET_PERMISSION_NAME =
            "SELECT id, name FROM permission_names WHERE id=?";

    private final static String ID = "user_id";
    private final static String PERMISSION = "permission_name_id";
    private final static String PERMISSION_NAME = "permission_name";


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

    public Optional<Permission> getPermissionByUserId(int id) {
       return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
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
        }, GET_USER_PERMISSION_BY_ID, id));

    }

    public Optional<Permission> addPermission(Permission permission) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return permission;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }, ADD_USER_PERMISSION, permission.getUserId(), permission.getPermissionNameId()));
    }

    public int deletePermissionByUserId(int id) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }, DELETE_USER_PERMISSION, id);
    }

    public Optional<Permission> updatePermission(Permission permission) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return permission;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        },UPDATE_USER_PERMISSION, permission.getPermissionNameId(), permission.getUserId()));
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

    public String getPermissionName(int id){
        StringBuilder sb = new StringBuilder();
        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()){
                    sb.append(rs.getString(PERMISSION_NAME));
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }, GET_PERMISSION_NAME, id);
        return sb.toString();
    }
}

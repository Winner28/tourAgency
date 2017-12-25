package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import com.epam.lab.ultimatewebservice.entity.Permission;
import com.epam.lab.ultimatewebservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private  UserDAO userDAO;
    private PermissionService permissionService;

    public User getUserById(int id) {
        return userDAO.getUserById(id).orElse(null);
    }

    public User createUser(User user) {
       return userDAO.addUser(user).orElse(null);
    }

    public User updateUser(User user) {
        return userDAO.updateUser(user).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email).orElse(null);
    }

    public List<User> getUsersList() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUserById(int id){
        return userDAO.deleteUserById(id);
    }

    public boolean createPermission(Permission permission) {
        return permissionService.createPermission(permission)!=null;
    }

    public boolean updatePermission(Permission permission) {
       return permissionService.updatePermission(permission) != null;
    }

    public boolean deletePermission(int userId){
        return permissionService.deletePermissionByUserId(userId);
    }

    public int getPermission(int userId) {
        Permission permission = permissionService.getPermissionByUserId(userId);
        if (permission == null) {
            return 0;
        }
        return permission.getPermissionNameId();
    }

    public List<Integer> getPermissionListById(int permissionId) {
        return permissionService.getUsersListByPermissionNameId(permissionId);
    }

    public List<Permission> getAllPermissions() {
        return permissionService.getPermissionsList();
    }
}

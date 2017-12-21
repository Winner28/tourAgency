package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.PermissionDAO;
import com.epam.lab.ultimatewebservice.entity.Permission;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private PermissionDAO permissionDAO;

    public Permission getPermissionByUserId(int userId){
        return permissionDAO.getPermissionByUserId(userId).orElse(new Permission());
    }

    public Permission createPermission(Permission permission){
        return permissionDAO.addPermission(permission).orElse(new Permission());
    }

    public Permission updatePermission(Permission permission){
        return permissionDAO.updatePermission(permission).orElse(new Permission());
    }

    public boolean deletePermissionByUserId(int userId){
        return permissionDAO.deletePermissionByUserId(userId) > 0;
    }

    public List<Permission> getPermissionsList() {
        return permissionDAO.getPermissionsList();
    }

    public List<Integer> getUsersListByPermissionNameId(int permissionsNameId){
        return permissionDAO.getUsersIdByPermissionNameId(permissionsNameId);
    }
}

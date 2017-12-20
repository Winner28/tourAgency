package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.PermissionDAO;
import com.epam.lab.ultimatewebservice.entity.Permission;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private PermissionDAO permissionDAO;

    public Permission getPermissionByUserId(){
        return permissionDAO.getUserPermissiom().orElse(new Permission());
    }

    public Permission createPermission(Permission permission){
        return permissionDAO.addUserPermission(permission).orElse(new Permission());
    }

    public Permission updatePermission(Permission permission){
        return permissionDAO.updatePermission(permission).orElse(new Permission());
    }
}

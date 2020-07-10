/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:52
 */

package fr.strow.core.module.permissions;

import com.google.inject.Inject;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.permissions.Role;
import fr.strow.persistence.beans.RoleBean;
import fr.strow.persistence.dao.PermissionsDao;
import fr.strow.persistence.dao.RoleDao;

import java.util.List;
import java.util.UUID;

public class GroupImplementationProperty implements PersistentImplementationProperty, Group {

    private final RoleDao rolesDao;
    private final PermissionsDao permissionsDao;

    private Role role;
    private List<String> permissions;

    @Inject
    public GroupImplementationProperty(RoleDao rolesDao, PermissionsDao permissionsDao) {
        this.rolesDao = rolesDao;
        this.permissionsDao = permissionsDao;
    }

    //TODO
    /*@Override
    public void load(UUID uuid) {
        RoleBean roleBean = rolesDao.loadRole(uuid);
        int roleId = roleBean.getRoleId();
        role = Role.getRoleById(roleId).orElseThrow(RuntimeException::new);

        PermissionsBean permissionsBean = permissionsDao.loadPermissions(roleId);
        permissions = permissionsBean.getPermissions();
    }*/

    @Override
    public void save(UUID uuid) {
        RoleBean roleBean = new RoleBean(uuid, role.getId());
        rolesDao.saveRole(roleBean);
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}

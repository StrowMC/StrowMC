/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:52
 */

package fr.strow.core.modules.permissions.properties;

import com.google.inject.Inject;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.permissions.Role;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.RoleBean;
import fr.strow.persistence.beans.permissions.PermissionsBean;
import fr.strow.persistence.dao.RoleDao;
import fr.strow.persistence.dao.permissions.PermissionsDao;

import java.util.Map;
import java.util.UUID;

public class GroupProperty implements Group, ImplementationProperty<Group> {

    private final RoleDao rolesDao;
    private final PermissionsDao permissionsDao;

    private UUID uuid;
    private Role role;
    private Map<String, Boolean> permissions;

    @Inject
    public GroupProperty(RoleDao rolesDao, PermissionsDao permissionsDao) {
        this.rolesDao = rolesDao;
        this.permissionsDao = permissionsDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        RoleBean roleBean = rolesDao.loadRole(uuid);
        int roleId = roleBean.getRoleId();
        role = Role.getRoleById(roleId).orElseThrow(RuntimeException::new);

        loadPermissions();

        return true;
    }

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
    public void setRole(Role role) {
        if (this.role != role) {
            this.role = role;

            save(uuid);

            loadPermissions();
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.get(permission);
    }

    @Override
    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    private void loadPermissions() {
        PermissionsBean permissionsBean = permissionsDao.loadPermissions(role.getId());
        permissions = permissionsBean.getPermissions();
    }
}

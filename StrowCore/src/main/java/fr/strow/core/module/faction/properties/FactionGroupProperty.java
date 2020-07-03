/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.factions.player.FactionGroup;
import fr.strow.api.game.factions.player.FactionRole;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.persistence.beans.factions.players.FactionPermissionsBean;
import fr.strow.persistence.beans.factions.players.FactionRoleBean;
import fr.strow.persistence.dao.factions.players.FactionPermissionsDao;
import fr.strow.persistence.dao.factions.players.FactionRoleDao;

import java.util.List;
import java.util.UUID;

public class FactionGroupProperty implements AbstractProperty, FactionGroup {

    private final FactionRoleDao factionRoleDao;
    private final FactionPermissionsDao factionPermissionsDao;

    private FactionRole role;
    private List<String> permissions;

    @Inject
    public FactionGroupProperty(FactionRoleDao factionRoleDao, FactionPermissionsDao factionPermissionsDao) {
        this.factionRoleDao = factionRoleDao;
        this.factionPermissionsDao = factionPermissionsDao;
    }

    @Override
    public void load(UUID uuid) {
        FactionRoleBean factionRoleBean = factionRoleDao.loadFactionRole(uuid);
        int factionRoleId = factionRoleBean.getRoleId();
        role = FactionRole.getRoleById(factionRoleId).orElseThrow(RuntimeException::new);

        loadPermissions(factionRoleId);
    }

    @Override
    public void save(UUID uuid) {
        FactionRoleBean factionRoleBean = new FactionRoleBean(uuid, role.getId());
        factionRoleDao.saveFactionRole(factionRoleBean);
    }

    @Override
    public FactionRole getRole() {
        return role;
    }

    @Override
    public void setRole(FactionRole role) {
        if (this.role != role) {
            this.role = role;

            loadPermissions(role.getId());
        }
    }

    private void loadPermissions(int factionRoleId) {
        FactionPermissionsBean factionPermissionsBean = factionPermissionsDao.loadFactionPermissions(factionRoleId);
        permissions = factionPermissionsBean.getPermissions();
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}

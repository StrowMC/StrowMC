/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.modules.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionGroup;
import fr.strow.api.game.faction.player.FactionRole;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.profile.FactionPermissionsBean;
import fr.strow.persistence.beans.factions.profile.FactionRoleBean;
import fr.strow.persistence.dao.factions.profile.FactionPermissionsDao;
import fr.strow.persistence.dao.factions.profile.FactionRoleDao;

import java.util.List;
import java.util.UUID;

public class FactionGroupProperty extends ImplementationProperty implements FactionGroup {

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
    public boolean load(UUID uuid) {
        FactionRoleBean factionRoleBean = factionRoleDao.loadFactionRole(uuid);
        int factionRoleId = factionRoleBean.getRoleId();
        role = FactionRole.getRoleById(factionRoleId).orElseThrow(RuntimeException::new);

        loadPermissions();

        return true;
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

            loadPermissions();
        }
    }

    private void loadPermissions() {
        FactionPermissionsBean factionPermissionsBean = factionPermissionsDao.loadFactionPermissions(role.getId());
        permissions = factionPermissionsBean.getPermissions();
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.factions.profile.FactionGroup;
import fr.strow.api.game.factions.profile.FactionRole;
import fr.strow.api.properties.ExplicitInitialisedProperty;
import fr.strow.api.properties.ImplicitInitialisedProperty;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.PropertyFactory;
import fr.strow.persistence.beans.factions.profile.FactionPermissionsBean;
import fr.strow.persistence.beans.factions.profile.FactionRoleBean;
import fr.strow.persistence.dao.factions.profile.FactionPermissionsDao;
import fr.strow.persistence.dao.factions.profile.FactionRoleDao;

import java.util.List;
import java.util.UUID;

public class FactionGroupProperty implements PersistentProperty, ExplicitInitialisedProperty<FactionGroupProperty.Factory>, ImplicitInitialisedProperty, FactionGroup {

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

        loadPermissions();
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

    public class Factory extends PropertyFactory {

        public void load(FactionRole role) {
            FactionGroupProperty.this.role = role;

            FactionGroupProperty.this.loadPermissions();
        }
    }
}

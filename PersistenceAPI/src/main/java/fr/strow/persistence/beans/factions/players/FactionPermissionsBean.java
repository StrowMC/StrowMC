/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:56
 */

package fr.strow.persistence.beans.factions.players;

import fr.strow.persistence.utils.Permission;
import fr.strow.persistence.utils.PermissionsProvider;

import java.util.List;

public class FactionPermissionsBean {

    @Permission("faction_disband")
    private final boolean factionDisband;

    public FactionPermissionsBean(boolean factionDisband) {
        this.factionDisband = factionDisband;
    }

    public List<String> getPermissions() {
        return PermissionsProvider.getPermissions(this);
    }
}

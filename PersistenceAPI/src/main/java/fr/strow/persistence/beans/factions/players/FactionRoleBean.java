/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:57
 */

package fr.strow.persistence.beans.factions.players;

import java.util.UUID;

public class FactionRoleBean {

    private final UUID uuid;
    private final int roleId;

    public FactionRoleBean(UUID uuid, int roleId) {
        this.uuid = uuid;
        this.roleId = roleId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getRoleId() {
        return roleId;
    }
}

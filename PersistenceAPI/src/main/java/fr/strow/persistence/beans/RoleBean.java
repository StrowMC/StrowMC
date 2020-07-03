/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 19:33
 */

package fr.strow.persistence.beans;

import java.util.UUID;

public class RoleBean {

    private final UUID uuid;
    private final int roleId;

    public RoleBean(UUID uuid, int roleId) {
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

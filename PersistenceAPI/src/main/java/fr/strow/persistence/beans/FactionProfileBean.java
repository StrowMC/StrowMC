/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:14
 */

package fr.strow.persistence.beans;

import java.util.UUID;

public class FactionProfileBean {

    private UUID uuid;
    private UUID factionUuid;
    private int roleId;
    private int power;
    private boolean claimer;

    public FactionProfileBean(UUID uuid, UUID factionUuid, int roleId, int power, boolean claimer) {
        this.uuid = uuid;
        this.factionUuid = factionUuid;
        this.roleId = roleId;
        this.power = power;
        this.claimer = claimer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }

    public void setFactionUuid(UUID factionUuid) {
        this.factionUuid = factionUuid;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isClaimer() {
        return claimer;
    }

    public void setClaimer(boolean claimer) {
        this.claimer = claimer;
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 22:05
 */

package fr.strow.persistence.beans;

import java.util.UUID;

public class PlayerBean {

    private UUID uuid;
    private String name;
    private int roleId;
    private int coins;

    public PlayerBean(UUID uuid, String name, int roleId, int coins) {
        this.uuid = uuid;
        this.name = name;
        this.roleId = roleId;
        this.coins = coins;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}

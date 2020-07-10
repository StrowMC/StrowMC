/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:53
 */

package fr.strow.persistence.beans.factions.profile;

import java.util.UUID;

public class FactionPowerBean {

    private final UUID uuid;
    private final int power;

    public FactionPowerBean(UUID uuid, int power) {
        this.uuid = uuid;
        this.power = power;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPower() {
        return power;
    }
}

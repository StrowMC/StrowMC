/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:15
 */

package fr.strow.persistence.beans;

import java.util.UUID;

public class EconomyBean {

    private final UUID uuid;
    private final int coins;

    public EconomyBean(UUID uuid, int coins) {
        this.uuid = uuid;
        this.coins = coins;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }
}

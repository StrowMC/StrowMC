/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:00
 */

package fr.strow.persistence.beans.factions.profile;

import java.util.UUID;

public class FactionUUIDBean {

    private final UUID uuid;
    private final UUID factionUuid;

    public FactionUUIDBean(UUID uuid, UUID factionUuid) {
        this.uuid = uuid;
        this.factionUuid = factionUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getFactionUuid() {
        return factionUuid;
    }
}

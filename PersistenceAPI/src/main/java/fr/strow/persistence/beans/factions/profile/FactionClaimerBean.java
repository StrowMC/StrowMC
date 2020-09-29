/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:58
 */

package fr.strow.persistence.beans.factions.profile;

import java.util.UUID;

public class FactionClaimerBean {

    private final UUID uuid;
    private final boolean claimer;

    public FactionClaimerBean(UUID uuid, boolean claimer) {
        this.uuid = uuid;
        this.claimer = claimer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isClaimer() {
        return claimer;
    }
}

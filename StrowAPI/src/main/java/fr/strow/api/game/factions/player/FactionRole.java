/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:49
 */

package fr.strow.api.game.factions.player;

import java.util.Arrays;
import java.util.Optional;

public enum FactionRole {

    MEMBER(1),
    MODERATOR(2),
    LEADER(3);

    private final int id;

    FactionRole(int id) {
        this.id = id;
    }

    public static Optional<FactionRole> getRoleById(int id) {
        return Arrays.stream(values())
                .filter(factionRole -> factionRole.id == id)
                .findFirst();
    }

    public int getId() {
        return id;
    }
}

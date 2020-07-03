/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:50
 */

package fr.strow.api.game.permissions;

import java.util.Arrays;
import java.util.Optional;

public enum Role {

    PLAYER(1),
    VIP(2),
    VIP_PLUS(3),
    BUILDER(4),
    DEVELOPER(5),
    MODERATOR(6),
    ADMINISTRATOR(7);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Optional<Role> getRoleById(int id) {
        return Arrays.stream(values())
                .filter(role -> role.id == id)
                .findFirst();
    }

    public int getId() {
        return id;
    }
}

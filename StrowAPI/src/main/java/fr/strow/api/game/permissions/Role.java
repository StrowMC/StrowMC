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

    PLAYER(0, "Joueur", "§7"),
    VIP(1, "VIP", "§a"),
    VIP_PLUS(2, "VIP+", "§b"),
    BUILDER(3, "Builder", "§e"),
    DEVELOPER(4, "Développeur", "§d") {
        @Override
        public String getPrefix() {
            return "Dev";
        }
    },
    MODERATOR(5, "Modérateur", "§6") {
        @Override
        public String getPrefix() {
            return "Modo";
        }
    },
    ADMINISTRATOR(6, "Administrateur", "§c") {
        @Override
        public String getPrefix() {
            return "Admin";
        }
    };

    private final int id;
    private final String name;
    private final String color;

    Role(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static Optional<Role> getRoleById(int id) {
        return Arrays.stream(values())
                .filter(role -> role.id == id)
                .findFirst();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return getName();
    }

    public String getColor() {
        return color;
    }
}

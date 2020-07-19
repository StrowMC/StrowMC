/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:49
 */

package fr.strow.api.game.faction.player;

import java.util.Arrays;
import java.util.Optional;

public enum FactionRole {

    MEMBER(0, "Membre", "§7-"),
    MODERATOR(1, "Modérateur", "§e+"),
    LEADER(2, "Leader", "§c*");

    private final int id;
    private final String name;
    private final String symbol;

    FactionRole(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public static FactionRole getLowestRole() {
        //noinspection OptionalGetWithoutIsPresent
        return FactionRole.getRoleById(0).get();
    }

    public static Optional<FactionRole> getRoleUnder(FactionRole role) {
        return getRoleById(role.getId() - 1);
    }

    public static Optional<FactionRole> getRoleAbove(FactionRole role) {
        return getRoleById(role.getId() + 1);
    }

    public static Optional<FactionRole> getRoleById(int id) {
        return Arrays.stream(values())
                .filter(factionRole -> factionRole.id == id)
                .findFirst();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}

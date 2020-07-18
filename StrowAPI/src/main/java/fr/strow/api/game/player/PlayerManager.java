/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:46
 */

package fr.strow.api.game.player;

import java.util.UUID;

public interface PlayerManager {

    boolean playerExists(UUID uuid);

    boolean playerExists(String name);

    StrowPlayer loadPlayer(UUID uuid);

    void unloadPlayer(UUID uuid);

    StrowPlayer getPlayer(UUID uuid);

    StrowPlayer getPlayer(String pseudo);

    PlayersCollection getPlayers();
}

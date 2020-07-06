/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:46
 */

package fr.strow.api.game.players;

import java.util.Collection;
import java.util.UUID;

public interface PlayerManager {

    void loadPlayer(UUID uuid);

    void unloadPlayer(UUID uuid);

    StrowPlayer getPlayer(UUID uuid);

    Collection<StrowPlayer> getPlayers();
}

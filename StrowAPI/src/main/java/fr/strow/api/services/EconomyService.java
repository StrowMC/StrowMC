/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:40
 */

package fr.strow.api.services;

import fr.strow.api.game.player.StrowPlayer;

import java.util.List;

public interface EconomyService {

    List<StrowPlayer> getRichestPlayers(int n);
}

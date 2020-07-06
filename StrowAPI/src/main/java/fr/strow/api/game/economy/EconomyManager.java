/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:40
 */

package fr.strow.api.game.economy;

import me.choukas.commands.utils.Tuple;

import java.util.List;

public interface EconomyManager {

    List<Tuple<String, Integer>> getRichestPlayers(int n);
}

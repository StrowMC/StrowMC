/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:50
 */

package fr.strow.api.game.faction;

import java.util.UUID;

public interface FactionManager {

    boolean factionExists(UUID uuid);

    boolean factionExists(String name);

    //Faction loadFaction(UUID uuid);

    //void unloadFactions();

    Faction getFaction(UUID uuid);

   // Faction getFaction(String name) throws IllegalArgumentException;

    //FactionsCollection getFactions();
}

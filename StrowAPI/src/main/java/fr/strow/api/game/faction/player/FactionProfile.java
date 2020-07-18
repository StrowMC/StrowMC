/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:52
 */

package fr.strow.api.game.faction.player;

import fr.strow.api.game.Property;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesOwner;

import java.util.UUID;

public interface FactionProfile extends PropertiesOwner<FactionProfile>, Property<StrowPlayer> {

    //void buildLeaderProfile(UUID factionUuid);

    //void buildMemberProfile(UUID factionUuid);
}

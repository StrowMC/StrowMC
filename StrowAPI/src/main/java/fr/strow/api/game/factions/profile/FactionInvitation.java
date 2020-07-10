/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:24
 */

package fr.strow.api.game.factions.profile;

import fr.strow.api.game.Property;
import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.players.StrowPlayer;

public interface FactionInvitation extends Property {

    StrowPlayer getSender();

    Faction getFaction();
}

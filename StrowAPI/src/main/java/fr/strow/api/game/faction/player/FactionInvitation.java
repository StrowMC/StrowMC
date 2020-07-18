/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:24
 */

package fr.strow.api.game.faction.player;

import fr.strow.api.game.Property;
import fr.strow.api.game.player.StrowPlayer;

import java.util.UUID;

public interface FactionInvitation extends Property<StrowPlayer> {

    UUID getSender();

    UUID getFaction();

    void build(UUID sender, UUID faction);
}

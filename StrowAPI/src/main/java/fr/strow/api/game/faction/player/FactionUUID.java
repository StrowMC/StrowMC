/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:37
 */

package fr.strow.api.game.faction.player;

import fr.strow.api.property.Property;

import java.util.UUID;

public interface FactionUUID extends Property<FactionProfile> {

    UUID getFactionUuid();

    void setFactionUuid(UUID factionUuid);
}

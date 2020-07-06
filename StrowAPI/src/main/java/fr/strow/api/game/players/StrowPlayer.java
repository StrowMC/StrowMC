/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:44
 */

package fr.strow.api.game.players;

import fr.strow.api.properties.PropertiesOwner;

import java.util.UUID;

public interface StrowPlayer extends PropertiesOwner {

    UUID getUniqueId();

}

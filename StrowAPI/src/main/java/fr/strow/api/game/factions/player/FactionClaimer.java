/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:43
 */

package fr.strow.api.game.factions.player;

import fr.strow.api.game.Property;

public interface FactionClaimer extends Property {

    boolean isClaimer();

    void setClaimer(boolean claimer);
}

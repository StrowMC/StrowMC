/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:44
 */

package fr.strow.api.game.factions.profile;

import fr.strow.api.properties.Property;

public interface FactionAutoClaiming extends Property {

    boolean isAutoClaiming();

    void setAutoClaim(boolean autoClaim);
}

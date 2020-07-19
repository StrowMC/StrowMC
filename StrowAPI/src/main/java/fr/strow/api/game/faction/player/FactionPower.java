/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:41
 */

package fr.strow.api.game.faction.player;

import fr.strow.api.property.Property;

public interface FactionPower extends Property<FactionProfile> {

    int getPower();

    void addPower(int amount);

    void removePower(int amount);

    void setPower(int power);
}

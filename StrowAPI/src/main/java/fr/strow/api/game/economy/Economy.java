/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:23
 */

package fr.strow.api.game.economy;

import fr.strow.api.property.Property;
import fr.strow.api.game.player.StrowPlayer;

public interface Economy extends Property<StrowPlayer> {

    int getCoins();

    void addCoins(int amount);

    void setCoins(int coins);

    void removeCoins(int amount);
}

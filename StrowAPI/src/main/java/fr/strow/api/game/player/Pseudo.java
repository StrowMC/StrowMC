/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:15
 */

package fr.strow.api.game.player;

import fr.strow.api.game.Property;

public interface Pseudo extends Property<StrowPlayer> {

    String getPseudo();

    void setPseudo(String pseudo);
}

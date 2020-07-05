/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:15
 */

package fr.strow.api.game.players;

import fr.strow.api.game.AbstractProperty;

public interface Pseudo extends AbstractProperty {

    String getPseudo();

    void setPseudo(String pseudo);
}
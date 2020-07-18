/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:44
 */

package fr.strow.api.game.player;

import fr.strow.api.game.Messenger;
import fr.strow.api.game.permissions.Role;
import fr.strow.api.property.PropertiesOwner;

import java.util.UUID;

public interface StrowPlayer extends PropertiesOwner<StrowPlayer>, Messenger {

    void connect();

    void disconnect();

    boolean isConnected();

    void build();
}

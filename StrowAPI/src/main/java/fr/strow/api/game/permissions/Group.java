/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:50
 */

package fr.strow.api.game.permissions;

import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.Property;

import java.util.Map;

public interface Group extends Property<StrowPlayer> {

    Role getRole();

    void setRole(Role role);

    boolean hasPermission(String permission);

    Map<String, Boolean> getPermissions();
}

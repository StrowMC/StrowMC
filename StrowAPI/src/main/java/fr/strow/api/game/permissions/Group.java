/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:50
 */

package fr.strow.api.game.permissions;

public interface Group {

    Role getRole();

    boolean hasPermission(String permission);
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:05
 */

package fr.strow.api.property;

import java.util.UUID;

public abstract class ImplementationProperty {

    public boolean load(UUID uuid) {
        return false;
    }

    public void save(UUID uuid) {

    }
}

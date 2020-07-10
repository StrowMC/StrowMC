/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:05
 */

package fr.strow.api.properties;

import fr.strow.api.game.Property;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

public abstract class ImplementationProperty<T extends Property> {

    public boolean load(UUID uuid) {
        return false;
    }

    public void save(UUID uuid) {
    }

    @SuppressWarnings("unchecked")
    public final Class<T> getImplementedProperty() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:06
 */

package fr.strow.core.module.player;

import fr.strow.api.game.Property;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.AbstractProperty;

import java.util.Collection;
import java.util.Map;

public class StrowPlayerImpl implements StrowPlayer {

    private final Map<Class<? extends AbstractProperty>, AbstractProperty> properties;

    public StrowPlayerImpl(Map<Class<? extends AbstractProperty>, AbstractProperty> properties) {
        this.properties = properties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Property> T get(Class<T> property) {
        return (T) properties.get(property);
    }

    @Override
    public Collection<AbstractProperty> getProperties() {
        return properties.values();
    }
}

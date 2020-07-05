/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:06
 */

package fr.strow.core.module.player;

import fr.strow.api.game.AbstractProperty;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.Property;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class StrowPlayerImpl implements StrowPlayer {

    private final Map<Class<? extends Property>, Property> properties;

    public StrowPlayerImpl(Map<Class<? extends Property>, Property> properties) {
        this.properties = properties;
    }

    @Override
    public <T extends Property> void registerProperty(T property) {
        properties.put(property.getClass(), property);
    }

    @Override
    public <T extends Property> void unregisterProperty(Class<T> property) {
        properties.remove(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractProperty> T get(Class<T> property) {
        return (T) properties.get(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractProperty> Optional<T> getOptionalProperty(Class<T> property) {
        return Optional.ofNullable((T) properties.get(property));
    }

    @Override
    public Collection<Property> getProperties() {
        return properties.values();
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(String format, Object... args) {

    }

    @Override
    public void sendMessage(BaseComponent component) {

    }
}

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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StrowPlayerImpl implements StrowPlayer {

    private final Map<Class<? extends Property>, Property> properties;
    private final Map<Class<? extends AbstractProperty>, AbstractProperty> abstractProperties;

    private final UUID uuid;

    @SuppressWarnings("unchecked")
    public StrowPlayerImpl(UUID uuid, Map<Class<? extends Property>, Property> properties) {
        this.properties = properties;
        this.abstractProperties = properties.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> (Class<? extends AbstractProperty>) entry.getKey(),
                        entry -> (AbstractProperty) entry.getValue()
                ));
        this.uuid = uuid;
    }
    //TODO
    //@Override
    public <T extends Property> void registerProperty(T property) {
        properties.put(property.getClass(), property);
    }

    //@Override
    public <T extends Property> void unregisterProperty(Class<T> property) {
        properties.remove(property);
    }

    //@Override
    public Collection<Property> getProperties() {
        return properties.values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractProperty> T getProperty(Class<T> property) {
        properties.get(property);
        return (T) abstractProperties.get(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractProperty> Optional<T> getOptionalProperty(Class<T> property) {
        return Optional.ofNullable((T) abstractProperties.get(property));
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }
}

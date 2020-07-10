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
import fr.strow.api.properties.ImplementationProperty;
import fr.strow.core.module.punishment.utils.BiKeyedMap;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StrowPlayerImpl implements StrowPlayer {

    private final UUID uuid;

    private final BiKeyedMap<Class<? extends ImplementationProperty<?>>, Class<? extends Property>, ImplementationProperty<?>> properties;

    public StrowPlayerImpl(UUID uuid,
                           BiKeyedMap<Class<? extends ImplementationProperty<?>>, Class<? extends Property>, ImplementationProperty<?>> properties) {
        this.uuid = uuid;
        this.properties = properties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Property> T getProperty(Class<T> property) {
        return (T) properties.get(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Property> Optional<T> getOptionalProperty(Class<T> property) {
        return Optional.ofNullable((T) properties.get(property));
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @SuppressWarnings("unchecked")
    public void registerProperty(ImplementationProperty<?> property) {
        properties.bind((Class<? extends ImplementationProperty<?>>) property.getClass(), property.getImplementedProperty(), property);
    }


    public <T extends ImplementationProperty<?>> void unregisterProperty(Class<T> property) {
        properties.remove(property);
    }

    public Collection<ImplementationProperty<?>> getProperties() {
        return properties.values();
    }
}

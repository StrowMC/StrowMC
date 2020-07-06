/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:06
 */

package fr.strow.core.module.player;

import fr.strow.api.game.AbstractProperty;
import fr.strow.api.game.AbstractService;
import fr.strow.api.game.factions.FactionName;
import fr.strow.api.game.factions.profile.FactionClaimer;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.Property;
import fr.strow.api.services.Service;
import fr.strow.core.module.faction.FactionImpl;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StrowPlayerImpl implements StrowPlayer {

    private final Map<Class<? extends Property>, Property> properties;
    private final Map<Class<? extends AbstractProperty>, AbstractProperty> abstractProperties;

    private final Map<Class<? extends AbstractService>, AbstractService> services;

    @SuppressWarnings("unchecked")
    public StrowPlayerImpl(Map<Class<? extends Property>, Property> properties, Map<Class<? extends Service>, Service> services) {
        this.properties = properties;
        this.abstractProperties = properties.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> (Class<? extends AbstractProperty>) entry.getKey(),
                        entry -> (AbstractProperty) entry.getValue()
                ));

        this.services = services.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> (Class<? extends AbstractService>) entry.getKey(),
                        entry -> (AbstractService) entry.getValue()
                ));
    }

    @Override
    public <T extends Property> void registerProperty(T property) {
        properties.put(property.getClass(), property);
    }

    @Override
    public <T extends Property> void unregisterProperty(Class<T> property) {
        properties.remove(property);
    }

    @Override
    public Collection<Property> getProperties() {
        return properties.values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractProperty> T getProperty(Class<T> property) {
        return (T) abstractProperties.get(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractProperty> Optional<T> getOptionalProperty(Class<T> property) {
        return Optional.ofNullable((T) abstractProperties.get(property));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractService> T getService(Class<T> service) {
        return (T) services.get(service);
    }
}

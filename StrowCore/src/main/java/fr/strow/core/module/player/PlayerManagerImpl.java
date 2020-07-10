/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:34
 */

package fr.strow.core.module.player;

import com.google.inject.Inject;
import fr.strow.api.game.Property;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.ImplementationProperty;
import fr.strow.core.module.punishment.utils.BiKeyedMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManagerImpl implements PlayerManager {

    private static final Map<UUID, StrowPlayer> players = new HashMap<>();

    private final PropertiesHandler propertiesHandler;

    @Inject
    public PlayerManagerImpl(PropertiesHandler propertiesHandler) {
        this.propertiesHandler = propertiesHandler;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadPlayer(UUID uuid) {
        BiKeyedMap<Class<? extends ImplementationProperty<?>>, Class<? extends Property>, ImplementationProperty<?>> properties = new BiKeyedMap<>();

        for (ImplementationProperty<?> implementationProperty : propertiesHandler.getProperties()) {
            if (implementationProperty.load(uuid)) {
                properties.bind((Class<? extends ImplementationProperty<?>>) implementationProperty.getClass(), implementationProperty.getImplementedProperty(), implementationProperty);
            }
        }

        StrowPlayerImpl player = new StrowPlayerImpl(uuid, properties);
        players.put(uuid, player);
    }

    @Override
    public void unloadPlayer(UUID uuid) {
        StrowPlayer player = players.get(uuid);

        for (ImplementationProperty<?> property : ((StrowPlayerImpl) player).getProperties()) {
            property.save(uuid);
        }

        players.remove(uuid);
    }

    @Override
    public StrowPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    @Override
    public Collection<StrowPlayer> getPlayers() {
        return players.values();
    }
}

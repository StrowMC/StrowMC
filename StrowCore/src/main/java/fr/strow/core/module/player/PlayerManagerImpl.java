/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:34
 */

package fr.strow.core.module.player;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.properties.OptionalPersistentProperty;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.properties.Property;

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

    @Override
    public void loadPlayer(UUID uuid) {
        Map<Class<? extends Property>, Property> propertiesHandler = new HashMap<>();

        for (Property property : this.propertiesHandler.getProperties()) {
            if (!(property instanceof OptionalPersistentProperty) || ((OptionalPersistentProperty) property).has(uuid)) {
                property.load(uuid);
                propertiesHandler.put(property.getClass(), property);
            }
        }

        StrowPlayer player = new StrowPlayerImpl(propertiesHandler);
        players.put(uuid, player);
    }

    @Override
    public void unloadPlayer(UUID uuid) {
        StrowPlayer player = players.get(uuid);

        for (Property property : player.getProperties()) {
            if (property instanceof PersistentProperty) {
                ((PersistentProperty) property).save(uuid);
            }
        }

        players.remove(uuid);
    }

    @Override
    public StrowPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }
}

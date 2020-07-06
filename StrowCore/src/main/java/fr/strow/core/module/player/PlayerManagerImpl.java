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
import fr.strow.api.properties.*;
import fr.strow.api.services.ServicesHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManagerImpl implements PlayerManager {

    private static final Map<UUID, StrowPlayer> players = new HashMap<>();

    private final PropertiesHandler propertiesHandler;
    private final ServicesHandler servicesHandler;

    @Inject
    public PlayerManagerImpl(PropertiesHandler propertiesHandler, ServicesHandler servicesHandler) {
        this.propertiesHandler = propertiesHandler;
        this.servicesHandler = servicesHandler;
    }

    @Override
    public void loadPlayer(UUID uuid) {
        Map<Class<? extends Property>, Property> properties = new HashMap<>();

        for (Property property : this.propertiesHandler.getProperties()) {
            if (property instanceof ImplicitInitialisedProperty && (!(property instanceof OptionalPersistentProperty) || ((OptionalPersistentProperty) property).has(uuid))) {
                ((ImplicitInitialisedProperty) property).load(uuid);
                properties.put(property.getClass(), property);
            }
        }
        //TODO
        /*StrowPlayerImpl player = new StrowPlayerImpl(properties, services);
        players.put(uuid, player);*/
    }

    @Override
    public void unloadPlayer(UUID uuid) {
        StrowPlayer player = players.get(uuid);
        //TODO
        /*for (Property property : player.getProperties()) {
            if (property instanceof PersistentProperty) {
                ((PersistentProperty) property).save(uuid);
            }
        }*/

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

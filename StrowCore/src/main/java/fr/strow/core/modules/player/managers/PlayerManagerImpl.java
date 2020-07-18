/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:34
 */

package fr.strow.core.modules.player.managers;

import com.google.inject.Inject;
import fr.strow.api.game.permissions.Role;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.PlayersCollection;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.api.property.PropertiesGrouping;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.player.StrowPlayerImpl;
import fr.strow.persistence.beans.PlayerBean;
import fr.strow.persistence.dao.PlayerDao;
import org.bukkit.Bukkit;

import java.util.*;

public class PlayerManagerImpl implements PlayerManager {

    private static final Map<UUID, StrowPlayer> players = new HashMap<>();

    private final PropertiesHandler propertiesHandler;
    private final PlayerDao playerDao;

    @Inject
    public PlayerManagerImpl(PropertiesHandler propertiesHandler, PlayerDao playerDao) {
        this.propertiesHandler = propertiesHandler;
        this.playerDao = playerDao;
    }

    @Override
    public boolean playerExists(UUID uuid) {
        return playerDao.playerExists(uuid);
    }

    @Override
    public boolean playerExists(String name) {
        UUID uuid = playerDao.getUUIDFromPseudo(name);

        return playerDao.playerExists(uuid);
    }

    @Override
    public StrowPlayer loadPlayer(UUID uuid) {
        StrowPlayer player;

        if (players.containsKey(uuid)) {
            player = players.get(uuid);
        } else {
            if (!playerExists(uuid)) {
                // TODO Change role
                PlayerBean bean = new PlayerBean(uuid, Bukkit.getPlayer(uuid).getName(), Role.DEVELOPER.getId(), 0);
                playerDao.createPlayer(bean);
            }

            player = new StrowPlayerImpl(propertiesHandler);
            players.put(uuid, player);
        }

        ((PropertiesGrouping<StrowPlayer>) player).load(uuid);

        player.connect();

        return player;
    }

    @Override
    public void unloadPlayer(UUID uuid) {
        StrowPlayer player = players.get(uuid);

        ((ImplementationProperty) player).save(uuid);

        player.disconnect();
    }

    @Override
    public StrowPlayer getPlayer(UUID uuid) {
        StrowPlayer player;

        if (players.containsKey(uuid)) {
            player = players.get(uuid);
        } else {
            if (playerExists(uuid)) {
                player = loadPlayer(uuid);
            } else {
                throw new IllegalArgumentException();
            }
        }

        return player;
    }

    @Override
    public StrowPlayer getPlayer(String pseudo) {
        Optional<StrowPlayer> optionalPlayer = getPlayers()
                .withPseudo(pseudo)
                .get();

        return optionalPlayer.orElseGet(() -> getPlayer(playerDao.getUUIDFromPseudo(pseudo)));
    }

    @Override
    public PlayersCollection getPlayers() {
        List<StrowPlayer> players = new ArrayList<>();
        List<UUID> uuids = playerDao.getPlayers();

        for (UUID uuid : uuids) {
            StrowPlayer player = getPlayer(uuid);
            players.add(player);
        }

        return new PlayersCollection(players);
    }
}

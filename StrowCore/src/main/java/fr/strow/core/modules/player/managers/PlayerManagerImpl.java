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
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.player.StrowPlayerImpl;
import fr.strow.persistence.dao.PlayerDao;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public boolean playerExists(String name) {
        return playerDao.playerExists(name);
    }

    @Override
    public StrowPlayer loadPlayer(String name) {
        UUID uuid;
        if (!playerDao.playerExists(name)) {
            do {
                uuid = UUID.randomUUID();
            } while (playerDao.playerNameExists(uuid));

            playerDao.createPlayer(name, uuid, name, Role.PLAYER.getId(), 0);
        } else {
            uuid = playerDao.getUUIDFromName(name);
        }

        StrowPlayer player = getPlayer(uuid);
        players.put(uuid, player);

        return player;
    }

    @Override
    public void unloadPlayer(String name) {
        UUID uuid = playerDao.getUUIDFromName(name);

        StrowPlayerImpl player = (StrowPlayerImpl) players.get(uuid);
        player.save(uuid);

        players.remove(uuid);
    }

    @Override
    public boolean isConnected(UUID uuid) {
        return players.containsKey(uuid);
    }

    @Override
    public StrowPlayer getPlayer(UUID uuid) {
        StrowPlayer player;

        if (players.containsKey(uuid)) {
            player = players.get(uuid);
        } else {
            if (playerDao.playerNameExists(uuid)) {
                player = loadPlayer(uuid);
            } else {
                throw new IllegalArgumentException();
            }
        }

        return player;
    }

    @Override
    public StrowPlayer getPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return getPlayer(sender);
        } else {
            throw new IllegalArgumentException("CommandSender must be a player");
        }
    }

    @Override
    public StrowPlayer getPlayer(Player player) {
        UUID uuid = playerDao.getUUIDFromName(player.getName());

        return getPlayer(uuid);
    }

    @Override
    public StrowPlayer getPlayer(String nickname) {
        UUID uuid = playerDao.getUUIDFromNickname(nickname);

        return getPlayer(uuid);
    }

    @Override
    public Map<UUID, StrowPlayer> getPlayers() {
       return players;
    }

    private StrowPlayer loadPlayer(UUID uuid) {
        StrowPlayerImpl player = new StrowPlayerImpl(propertiesHandler);
        player.load(uuid);

        players.put(uuid, player);

        return player;
    }
}

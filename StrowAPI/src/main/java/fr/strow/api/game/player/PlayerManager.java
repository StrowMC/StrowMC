/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:46
 */

package fr.strow.api.game.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface PlayerManager {

    boolean playerExists(String name);

    StrowPlayer loadPlayer(String name);

    void unloadPlayer(String name);

    boolean isConnected(UUID uuid);

    StrowPlayer getPlayer(UUID uuid);

    StrowPlayer getPlayer(CommandSender sender);

    StrowPlayer getPlayer(Player player);

    StrowPlayer getPlayer(String nickname);

    Map<UUID, StrowPlayer> getPlayers();
}

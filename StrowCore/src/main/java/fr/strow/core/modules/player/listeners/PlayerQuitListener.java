/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 29/06/2020 19:58
 */

package fr.strow.core.modules.player.listeners;

import com.google.inject.Inject;
import fr.strow.api.game.permissions.PermissionsManager;
import fr.strow.api.game.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerManager playerManager;
    private final PermissionsManager permissionsManager;

    @Inject
    public PlayerQuitListener(PlayerManager playerManager, PermissionsManager permissionsManager) {
        this.playerManager = playerManager;
        this.permissionsManager = permissionsManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();

        playerManager.unloadPlayer(name);
        permissionsManager.unloadPermissions(name);
    }
}

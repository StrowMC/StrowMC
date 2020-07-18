/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 29/06/2020 19:57
 */

package fr.strow.core.modules.player.listeners;

import com.google.inject.Inject;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.permissions.Role;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public PlayerJoinListener(PlayerManager playerManager, Messaging messaging) {
        this.playerManager = playerManager;
        this.messaging = messaging;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        boolean newcomer = !playerManager.playerExists(uuid);

        StrowPlayer player = playerManager.loadPlayer(uuid);

        String joinMessage = null;

        if (newcomer) {
            joinMessage = String.format("%s a rejoint le serveur pour la première fois !", player.getProperty(Pseudo.class).getPseudo());
            messaging.sendMessage(player, "Bienvenue %s !", player.getProperty(Pseudo.class).getPseudo());
        } else {
            Role role = player.getProperty(Group.class).getRole();

            if (role.getId() > Role.PLAYER.getId()) {
                joinMessage = String.format("§f[%s%s§f] %s%s §fa rejoint le serveur !",
                        role.getColor(),
                        role.getPrefix(),
                        role.getColor(),
                        player.getProperty(Pseudo.class)
                                .getPseudo());
            }
        }

        event.setJoinMessage(joinMessage);

        //ConnectionPunishmentManager.checkPunishment(playerManager, uuid);
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:06
 */

package fr.strow.core.modules.player;

import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.permissions.Role;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesEntity;
import fr.strow.api.property.PropertiesHandler;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class StrowPlayerImpl extends PropertiesEntity<StrowPlayer> implements StrowPlayer {

    private boolean connected;

    public StrowPlayerImpl(PropertiesHandler propertiesHandler) {
        super(StrowPlayer.class, propertiesHandler);
    }

    @Override
    public void connect() {
        connected = true;
    }

    @Override
    public void disconnect() {
        connected = false;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void build() {
        getProperty(Pseudo.class).setPseudo(Bukkit.getPlayer(uuid).getName());
        getProperty(Group.class).setRole(Role.PLAYER);
        getProperty(Economy.class).setCoins(100);
    }

    @Override
    public Collection<UUID> getRecipients() {
        return Collections.singleton(getUniqueId());
    }
}

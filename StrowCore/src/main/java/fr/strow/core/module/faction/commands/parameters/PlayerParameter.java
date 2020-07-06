/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:09
 */

package fr.strow.core.module.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;

    @Inject
    public PlayerParameter(PlayerManager playerManager) {
        super("player");

        this.playerManager = playerManager;
    }

    @Override
    public Collection<StrowPlayer> tabComplete(CommandSender sender) {
        return playerManager.getPlayers();
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<String>() {
                    @Override
                    public boolean check(String arg) {
                        return Bukkit.getPlayer(arg) != null;
                    }

                    @Override
                    public String getMessage(String arg) {
                        return "Ce joueur n'est pas connecté";
                    }
                }
        );
    }

    @Override
    public StrowPlayer get(String arg) {
        return playerManager.getPlayer(Bukkit.getPlayer(arg).getUniqueId());
    }
}

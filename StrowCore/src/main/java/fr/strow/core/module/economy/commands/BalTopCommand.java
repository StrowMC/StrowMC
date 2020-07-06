/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:25
 */

package fr.strow.core.module.economy.commands;

import fr.strow.api.game.economy.EconomyManager;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.utils.Tuple;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BalTopCommand extends EvolvedCommand {

    private static final int TOP = 10;

    private final EconomyManager economyManager;

    public BalTopCommand(EconomyManager economyManager) {
        super(CommandDescription.builder()
                .withName("baltop")
                .withDescription("Affiche le top 10 des joueurs les plus riches du serveur")
                .build());

        this.economyManager = economyManager;
    }

    @Override
    protected void execute(CommandSender sender) {
        List<Tuple<String, Integer>> players = economyManager.getRichestPlayers(TOP);

        for (int i = 0; i < TOP; i++) {
            Tuple<String, Integer> player = players.get(i);

            sender.sendMessage(String.format("%d. %s : %d",
                    i + 1,
                    player.getKey(),
                    player.getValue()));
        }
    }
}

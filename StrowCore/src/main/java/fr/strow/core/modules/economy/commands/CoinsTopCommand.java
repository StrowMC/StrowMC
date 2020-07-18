/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:25
 */

package fr.strow.core.modules.economy.commands;

import com.google.inject.Inject;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.EconomyService;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CoinsTopCommand extends EvolvedCommand {

    private static final int TOP = 10;

    private final EconomyService economyService;

    @Inject
    public CoinsTopCommand(EconomyService economyService) {
        super(CommandDescription.builder()
                .withName("top")
                .withDescription("Afficher les " + TOP + " joueurs les plus riches du serveur")
                .build());

        this.economyService = economyService;

        define();
    }

    @Override
    protected void execute(CommandSender sender) {
        List<StrowPlayer> players = economyService.getRichestPlayers(TOP);

        for (int i = 0; i < TOP; i++) {
            StrowPlayer player = players.get(i);

            sender.sendMessage(String.format("%d. %s : %d",
                    i + 1,
                    player.getProperty(Pseudo.class).getPseudo(),
                    player.getProperty(Economy.class).getCoins())
            );
        }
    }
}

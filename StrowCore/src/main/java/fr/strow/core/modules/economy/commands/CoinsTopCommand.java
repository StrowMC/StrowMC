/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:25
 */

package fr.strow.core.modules.economy.commands;

import com.google.inject.Inject;
import fr.strow.persistence.dao.EconomyDao;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class CoinsTopCommand extends EvolvedCommand {

    private static final int TOP = 10;

    private final EconomyDao economyDao;

    @Inject
    public CoinsTopCommand(EconomyDao economyDao) {
        super(CommandDescription.builder()
                .withName("top")
                .withDescription("Afficher les joueur dont le solde est le plus élevé")
                .build());

        this.economyDao = economyDao;

        define();
    }

    @Override
    protected void execute(CommandSender sender) {
        Map<String, Integer> players = economyDao.getRichestPlayers(TOP);

        int rank = 1;
        for (Map.Entry<String, Integer> player : players.entrySet()) {
            String pseudo = player.getKey();
            int coins = player.getValue();

            sender.sendMessage(String.format("%d. %s : %d", rank, pseudo, coins));

            rank++;
        }
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:24
 */

package fr.strow.core.modules.economy.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.utils.commands.requirements.SenderIsPlayerRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public CoinsCommand(CommandService commandService, PlayerManager playerManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("coins")
                .withAliases("balance", "bal")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.messaging = messaging;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsPlayerRequirement.class));

        addSubCommand(commandService.getCommand(CoinsAddCommand.class));
        addSubCommand(commandService.getCommand(CoinsRemoveCommand.class));
        addSubCommand(commandService.getCommand(CoinsSetCommand.class));
        addSubCommand(commandService.getCommand(CoinsTopCommand.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender.getName());

        messaging.sendMessage(strowSender, "Vous possédez %s$ sur votre compte", strowSender.getProperty(Economy.class).getCoins());
    }
}

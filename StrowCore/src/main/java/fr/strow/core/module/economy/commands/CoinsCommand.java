/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:24
 */

package fr.strow.core.module.economy.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.MessageService;
import fr.strow.core.utils.commands.conditions.SenderIsPlayerRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand extends EvolvedCommand {

    private final CommandsManager commandsManager;
    private final PlayerManager playerManager;
    private final MessageService messageService;

    @Inject
    public CoinsCommand(CommandsManager commandsManager, PlayerManager playerManager, MessageService messageService) {
        super(CommandDescription.builder()
                .withName("balance")
                .withAliases("coins")
                .build());

        this.commandsManager = commandsManager;
        this.playerManager = playerManager;
        this.messageService = messageService;

        define();
    }

    @Override
    protected void define() {
        addCondition(commandsManager.getCondition(SenderIsPlayerRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());
        //TODO
        messageService.sendMessage(strowSender.getUniqueId(), "Vous possédez %s$ sur votre compte"/*, strowSender.get(Economy.class)*/);
    }
}

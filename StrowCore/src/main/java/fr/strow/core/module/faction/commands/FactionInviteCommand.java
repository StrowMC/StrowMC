/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:01
 */

package fr.strow.core.module.faction.commands;

import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.core.module.faction.commands.parameters.PlayerParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionInviteCommand extends EvolvedCommand {

    private final CommandsManager commandsManager;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;

    public FactionInviteCommand(CommandsManager commandsManager, PlayerManager playerManager, FactionManager factionManager) {
        super(CommandDescription.builder()
                .withName("invite")
                .withDescription("Inviter un joueur à rejoindre sa faction")
                .build());

        this.commandsManager = commandsManager;
        this.playerManager = playerManager;
        this.factionManager = factionManager;

        define();
    }

    @Override
    protected void define() {
        addParam(commandsManager.getParameter(PlayerParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());
        StrowPlayer target = readArg();


        factionManager.invitePlayer(strowSender, target);
    }
}

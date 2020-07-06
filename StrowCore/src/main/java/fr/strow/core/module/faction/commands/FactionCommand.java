/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core.module.faction.commands;

import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.players.PlayerManager;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;

public class FactionCommand extends EvolvedCommand {

    private final CommandsManager commandsManager;

    public FactionCommand(CommandsManager commandsManager) {
        super(CommandDescription.builder()
                .withName("faction")
                .withAliases("f")
                .build());

        this.commandsManager = commandsManager;

        define();
    }

    @Override
    public void define() {
        addSubCommand(commandsManager.getCommand(FactionCreateCommand.class));
        addSubCommand(commandsManager.getCommand(FactionDemoteCommand.class));
    }
}

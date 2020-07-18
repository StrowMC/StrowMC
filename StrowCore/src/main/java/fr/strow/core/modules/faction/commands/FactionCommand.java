/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:26
 */

package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;

public class FactionCommand extends EvolvedCommand {

    private final CommandService commandService;

    @Inject
    public FactionCommand(CommandService commandService) {
        super(CommandDescription.builder()
                .withName("faction")
                .withAliases("f")
                .build());

        this.commandService = commandService;

        define();
    }

    @Override
    public void define() {
        addSubCommand(commandService.getCommand(FactionCreateCommand.class));
        addSubCommand(commandService.getCommand(FactionDemoteCommand.class));
        addSubCommand(commandService.getCommand(FactionDescriptionCommand.class));
        addSubCommand(commandService.getCommand(FactionDisbandCommand.class));
        addSubCommand(commandService.getCommand(FactionInviteCommand.class));
        addSubCommand(commandService.getCommand(FactionJoinCommand.class));
    }
}

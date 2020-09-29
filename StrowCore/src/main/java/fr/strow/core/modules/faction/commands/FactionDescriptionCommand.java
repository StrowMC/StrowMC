/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:02
 */

package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionDescription;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.parameters.FactionDescriptionParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

public class FactionDescriptionCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;

    @Inject
    public FactionDescriptionCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("description")
                .withAliases("desc")
                .withPermission("faction.description")
                .withDescription("Changer la description de votre faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));

        addParameter(commandService.getParameter(FactionDescriptionParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        String description = readArg();

        Faction faction = factionManager.getFaction(strowSender
                .getProperty(FactionProfile.class)
                .getProperty(FactionUUID.class)
                .getFactionUuid());

        if (!faction.getOptionalProperty(FactionDescription.class).isPresent()) {
            faction.registerProperty(FactionDescription.class);
        }

        faction.getProperty(FactionDescription.class).setDescription(description);

        messaging.sendMessage(strowSender, "La description de votre faction a bien été mise à jour.");
    }
}

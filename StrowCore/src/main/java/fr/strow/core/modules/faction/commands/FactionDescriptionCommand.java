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
import fr.strow.api.property.PropertiesEntity;
import fr.strow.core.modules.faction.commands.parameters.FactionDescriptionParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class FactionDescriptionCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;

    @Inject
    public FactionDescriptionCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager) {
        super(CommandDescription.builder()
                .withName("description")
                .withAliases("desc")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));

        addParameter(commandService.getParameter(FactionDescriptionParameter.class), false);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

        Optional<String> description = readOptionalArg();

        if (description.isPresent()) {
            Faction faction = factionManager.getFaction(strowSender
                    .getProperty(FactionProfile.class)
                    .getProperty(FactionUUID.class)
                    .getFactionUuid());

            if (!faction.getOptionalProperty(FactionDescription.class).isPresent()) {
                ((PropertiesEntity<Faction>) faction).registerProperty(FactionDescription.class);
            }

            faction.getProperty(FactionDescription.class).setDescription(description.get());
        } else {

        }
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 16:59
 */

package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.events.FactionCreateEvent;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionRole;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.core.modules.faction.commands.parameters.FactionDescriptionParameter;
import fr.strow.core.modules.faction.commands.parameters.FactionNameParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsNotInFactionRequirement;
import fr.strow.persistence.dao.factions.FactionDao;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class FactionCreateCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final JavaPlugin plugin;
    private final FactionDao factionDao;

    @Inject
    public FactionCreateCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, JavaPlugin plugin, FactionDao factionDao) {
        super(CommandDescription.builder()
                .withName("create")
                .withAliases("c")
                .withDescription("Créer une faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.plugin = plugin;
        this.factionDao = factionDao;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsNotInFactionRequirement.class));

        addParameter(commandService.getParameter(FactionNameParameter.class), true);
        addParameter(commandService.getParameter(FactionDescriptionParameter.class), false);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer leader = playerManager.getPlayer(sender);
        UUID leaderUuid = leader.getUniqueId();

        String name = readArg();

        FactionCreateEvent event = new FactionCreateEvent(leaderUuid);
        plugin.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            UUID factionUuid;
            do {
                factionUuid = UUID.randomUUID();
            } while (factionManager.factionExists(factionUuid));

            // Register property
            leader.registerProperty(FactionProfile.class, new FactionProfile.Factory(factionUuid, FactionRole.LEADER, 100, true));

            String prefix = name.substring(0, 3);
            String description = readOptionalArg(String.class).orElse(null);

            // Insert faction in SQL
            factionDao.createFaction(factionUuid, name, prefix, leaderUuid, description, 0);

            /*// Load faction
            factionManager.loadFaction(factionUuid);*/
        }
    }
}

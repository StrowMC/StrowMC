/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:01
 */

package fr.strow.core.modules.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.FactionMembers;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.permissions.PermissionsManager;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.Nickname;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import fr.strow.persistence.dao.factions.FactionDao;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class FactionDisbandCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final PermissionsManager permissionsManager;
    private final Messaging messaging;
    private final FactionDao factionDao;

    @Inject
    public FactionDisbandCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, PermissionsManager permissionsManager, Messaging messaging, FactionDao factionDao) {
        super(CommandDescription.builder()
                .withName("disband")
                .withPermission("faction.disband")
                .withDescription("Dissoudre votre faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.permissionsManager = permissionsManager;
        this.messaging = messaging;
        this.factionDao = factionDao;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        Faction faction = factionManager.getFaction(strowSender
                .getProperty(FactionProfile.class)
                .getProperty(FactionUUID.class)
                .getFactionUuid());

        messaging.sendMessage(faction, "La faction a été dissoue par %s", strowSender.getProperty(Nickname.class).getNickname());

        factionDao.deleteFaction(faction.getUniqueId());

        for (UUID uuid : faction.getProperty(FactionMembers.class).getMembers()) {
            StrowPlayer player = playerManager.getPlayer(uuid);

            player.unregisterProperty(FactionProfile.class);

            permissionsManager.reloadPermissions(player);
        }
    }
}

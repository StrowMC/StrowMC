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
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.game.permissions.PermissionsManager;
import fr.strow.api.game.player.Nickname;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.parameters.MemberFromSenderFactionParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;

public class FactionKickCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final PermissionsManager permissionsManager;
    private final Messaging messaging;

    @Inject
    public FactionKickCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, PermissionsManager permissionsManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("kick")
                .withPermission("faction.kick")
                .withDescription("Exclure un joueur de votre faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.permissionsManager = permissionsManager;
        this.messaging = messaging;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));

        addParameter(commandService.getParameter(MemberFromSenderFactionParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        StrowPlayer player = readArg();

        Faction faction = factionManager.getFaction(player
                .getProperty(FactionProfile.class)
                .getProperty(FactionUUID.class)
                .getFactionUuid());

        player.unregisterProperty(FactionProfile.class);

        permissionsManager.reloadPermissions(player);

        messaging.sendMessage(faction, "%s a été exclu par %s",
                player.getProperty(Nickname.class).getNickname(),
                strowSender.getProperty(Nickname.class).getNickname());
    }
}

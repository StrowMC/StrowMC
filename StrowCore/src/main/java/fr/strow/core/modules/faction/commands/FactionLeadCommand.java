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
import fr.strow.api.game.faction.player.FactionGroup;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionRole;
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

import java.util.UUID;

public class FactionLeadCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final PermissionsManager permissionsManager;
    private final Messaging messaging;

    @Inject
    public FactionLeadCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, PermissionsManager permissionsManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("lead")
                .withPermission("faction.lead")
                .withDescription("Confier le leadership de la faction à un membre")
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

        StrowPlayer target = readArg();

        FactionProfile senderProfile = strowSender.getProperty(FactionProfile.class);
        FactionGroup senderGroup = senderProfile.getProperty(FactionGroup.class);
        if (senderGroup.getRole().equals(FactionRole.LEADER)) {
            //noinspection OptionalGetWithoutIsPresent
            senderGroup.setRole(FactionRole.getRoleUnder(FactionRole.LEADER).get());
        }

        target.getProperty(FactionProfile.class).getProperty(FactionGroup.class).setRole(FactionRole.LEADER);

        permissionsManager.reloadPermissions(strowSender);
        permissionsManager.reloadPermissions(target);

        UUID factionUuid = senderProfile.getProperty(FactionUUID.class).getFactionUuid();
        Faction faction = factionManager.getFaction(factionUuid);

        messaging.sendMessage(faction, "%s cède sa place de chef à %s !" +
                strowSender.getProperty(Nickname.class).getNickname(),
                target.getProperty(Nickname.class).getNickname());
    }
}

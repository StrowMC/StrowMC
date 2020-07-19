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
import fr.strow.api.game.faction.FactionName;
import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionRole;
import fr.strow.api.game.permissions.PermissionsManager;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.Nickname;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsNotInFactionRequirement;
import fr.strow.core.utils.commands.requirements.SenderIsPlayerRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionJoinCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final PermissionsManager permissionsManager;
    private final Messaging messaging;

    @Inject
    public FactionJoinCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, PermissionsManager permissionsManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("join")
                .withDescription("Rejoindre une faction à laquelle vous êtes invité")
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
        addRequirement(commandService.getRequirement(SenderIsNotInFactionRequirement.class));
        addRequirement(commandService.getRequirement(SenderHasInvitationRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        FactionInvitation factionInvitation = strowSender.getProperty(FactionInvitation.class);

        joinFaction(strowSender, factionInvitation.getFaction());
    }

    private void joinFaction(StrowPlayer player, UUID factionUuid) {
        Faction faction = factionManager.getFaction(factionUuid);
        UUID uuid = player.getUniqueId();

        // Register property
        player.registerProperty(FactionProfile.class, new FactionProfile.Factory(factionUuid, FactionRole.MEMBER, 0, false));

        messaging.sendMessage(faction, "%s a rejoint la faction !", player.getProperty(Nickname.class).getNickname());

        // Add the member to the faction members list
        faction.getProperty(FactionMembers.class).addMember(uuid);

        permissionsManager.reloadPermissions(player);

        messaging.sendMessage(player, "Vous faites maintenant parti de %s !", faction.getProperty(FactionName.class).getName());
    }

    private static class SenderHasInvitationRequirement extends Requirement {

        private final SenderIsPlayerRequirement senderIsPlayerRequirement;
        private final PlayerManager playerManager;
        private final Messaging messaging;

        @Inject
        public SenderHasInvitationRequirement(SenderIsPlayerRequirement senderIsPlayerRequirement, PlayerManager playerManager, Messaging messaging) {
            this.senderIsPlayerRequirement = senderIsPlayerRequirement;
            this.playerManager = playerManager;
            this.messaging = messaging;
        }

        @Override
        public List<Condition<CommandSender>> getConditions() {
            List<Condition<CommandSender>> conditions = new ArrayList<>(senderIsPlayerRequirement.getConditions());

            conditions.add(
                    new Condition<CommandSender>() {
                        @Override
                        public boolean check(CommandSender sender) {
                            StrowPlayer strowSender = playerManager.getPlayer(sender);

                            return strowSender.getOptionalProperty(FactionInvitation.class).isPresent();
                        }

                        @Override
                        public BaseComponent getMessage(CommandSender sender) {
                            return messaging.errorMessage("Vous n'avez pas reçu d'invitation à rejoindre une faction");
                        }
                    }
            );

            return conditions;
        }
    }
}

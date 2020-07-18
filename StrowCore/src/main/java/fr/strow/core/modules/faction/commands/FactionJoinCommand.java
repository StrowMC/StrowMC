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
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesEntity;
import fr.strow.api.property.PropertiesGrouping;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsNotInFactionRequirement;
import fr.strow.core.utils.commands.requirements.SenderIsPlayerRequirement;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.dao.factions.profile.FactionProfileDao;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class FactionJoinCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;
    private final FactionProfileDao factionProfileDao;

    @Inject
    public FactionJoinCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging, FactionProfileDao factionProfileDao) {
        super(CommandDescription.builder()
                .withName("join")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;
        this.factionProfileDao = factionProfileDao;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsNotInFactionRequirement.class));
        addRequirement(commandService.getRequirement(SenderHasInvitationRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

        FactionInvitation factionInvitation = strowSender.getProperty(FactionInvitation.class);

        joinFaction(strowSender, factionInvitation.getFaction());
    }

    private void joinFaction(StrowPlayer player, UUID factionUuid) {
        Faction faction = factionManager.getFaction(factionUuid);
        UUID uuid = player.getUniqueId();

        // Set the player faction profile
        FactionProfileBean profileBean = new FactionProfileBean(uuid, factionUuid, FactionRole.MEMBER.getId(), 0, false);
        factionProfileDao.createProfile(profileBean);

        ((PropertiesEntity<StrowPlayer>) player).registerProperty(FactionProfile.class);
        ((PropertiesGrouping<FactionProfile>) player.getProperty(FactionProfile.class)).load(uuid);

        messaging.sendMessage(faction, "%s a rejoint la faction !", player.getProperty(Pseudo.class).getPseudo());

        // Add the member to the faction members list
        faction.getProperty(FactionMembers.class).addMember(uuid);

        messaging.sendMessage(player, "Vous faites maintenant parti de %s !", faction.getProperty(FactionName.class).getName());
    }

    private static class SenderHasInvitationRequirement extends Requirement {

        private final SenderIsPlayerRequirement senderIsPlayerRequirement;
        private final PlayerManager playerManager;

        @Inject
        public SenderHasInvitationRequirement(SenderIsPlayerRequirement senderIsPlayerRequirement, PlayerManager playerManager) {
            this.senderIsPlayerRequirement = senderIsPlayerRequirement;
            this.playerManager = playerManager;
        }

        @Override
        public List<Condition<CommandSender>> getConditions() {
            List<Condition<CommandSender>> conditions = senderIsPlayerRequirement.getConditions();

            conditions.add(
                    new Condition<CommandSender>() {
                        @Override
                        public boolean check(CommandSender sender) {
                            StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

                            return strowSender.getOptionalProperty(FactionInvitation.class).isPresent();
                        }

                        @Override
                        public String getMessage(CommandSender sender) {
                            return "Vous n'avez pas reçu d'invitation à rejoindre une faction";
                        }
                    }
            );

            return conditions;
        }
    }
}

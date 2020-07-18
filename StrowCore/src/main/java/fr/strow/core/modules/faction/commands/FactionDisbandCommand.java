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
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesEntity;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import fr.strow.persistence.dao.factions.FactionDao;
import fr.strow.persistence.dao.factions.FactionMembersDao;
import fr.strow.persistence.dao.factions.profile.FactionProfileDao;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FactionDisbandCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;
    private final FactionDao factionDao;
    private final FactionProfileDao factionProfileDao;
    private final FactionMembersDao factionMembersDao;

    @Inject
    public FactionDisbandCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging, FactionDao factionDao, FactionProfileDao factionProfileDao, FactionMembersDao factionMembersDao) {
        super(CommandDescription.builder()
                .withName("disband")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;
        this.factionDao = factionDao;
        this.factionProfileDao = factionProfileDao;
        this.factionMembersDao = factionMembersDao;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

        Faction faction = factionManager.getFaction(strowSender
                .getProperty(FactionProfile.class)
                .getProperty(FactionUUID.class)
                .getFactionUuid());

        messaging.sendMessage(faction, "La faction a été dissoue par %s",
                strowSender
                        .getProperty(Pseudo.class)
                        .getPseudo());

        factionDao.deleteFaction(faction.getUniqueId());

        factionMembersDao.deleteMembers(faction.getUniqueId());

        for (UUID uuid : faction.getProperty(FactionMembers.class).getMembers()) {
            StrowPlayer player = playerManager.getPlayer(uuid);

            ((PropertiesEntity<StrowPlayer>) player).unregisterProperty(FactionProfile.class);

            factionProfileDao.deleteProfile(uuid);
        }
    }
}

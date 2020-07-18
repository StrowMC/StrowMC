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
import fr.strow.api.game.faction.FactionName;
import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.Pseudo;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesEntity;
import fr.strow.api.services.Messaging;
import fr.strow.api.services.Scheduler;
import fr.strow.core.modules.faction.commands.parameters.GuestParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class FactionInviteCommand extends EvolvedCommand {

    private static final int TIMER = 5;

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;
    private final Scheduler scheduler;

    @Inject
    public FactionInviteCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging, Scheduler scheduler) {
        super(CommandDescription.builder()
                .withName("invite")
                .withDescription("Inviter un joueur à rejoindre sa faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;
        this.scheduler = scheduler;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));

        addParameter(commandService.getParameter(GuestParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

        StrowPlayer receiver = readArg();

        sendInvitation(strowSender, receiver);
    }

    private void sendInvitation(StrowPlayer sender, StrowPlayer receiver) {
        Faction faction = factionManager.getFaction(sender.getProperty(FactionProfile.class).getUniqueId());

        ((PropertiesEntity<StrowPlayer>) receiver).registerProperty(FactionInvitation.class);
        receiver.getProperty(FactionInvitation.class).build(sender.getUniqueId(), faction.getUniqueId());

        messaging.sendMessage(receiver, "Vous avez reçu une invitation pour rejoindre la faction de %s.", receiver.getProperty(Pseudo.class).getPseudo());
        messaging.sendMessage(receiver, "Cette invitation expirera dans %u minutes.", TIMER);

        TextComponent component = new TextComponent("Cliquez ici pour accepter cette invitation");
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/faction join %s", faction.getProperty(FactionName.class).getName())));

        // Delete the property after TIMER minutes
        scheduler.runTaskLater(() -> ((PropertiesEntity<StrowPlayer>) receiver).unregisterProperty(FactionInvitation.class), TIMER, TimeUnit.MINUTES);
    }
}

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
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionLeaveCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final Messaging messaging;

    @Inject
    public FactionLeaveCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("leave")
                .withDescription("Quitter une faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messaging = messaging;
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());
        /*FactionRole role = strowSender.get(FactionProfile.class).getFactionGroup().getRole();
        //TODO
        if (role == FactionRole.LEADER) {
            TextComponent text = new TextComponent("Pour quitter votre faction, vous devez la dissoudre");
            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/faction disband"));
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez ici pour dissoudre votre faction").create()));

            messageService.sendMessage(strowSender.getUniqueId(), text);
        } else {
            factionManager.leaveFaction(strowSender);
        }*/
    }
}

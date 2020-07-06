/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:01
 */

package fr.strow.core.module.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.api.service.MessageService;
import fr.strow.core.module.faction.commands.conditions.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionLeaveCommand extends EvolvedCommand {

    private final CommandsManager commandsManager;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final MessageService messageService;

    @Inject
    public FactionLeaveCommand(CommandsManager commandsManager, PlayerManager playerManager, FactionManager factionManager, MessageService messageService) {
        super(CommandDescription.builder()
                .withName("leave")
                .withDescription("Quitter une faction")
                .build());

        this.commandsManager = commandsManager;
        this.playerManager = playerManager;
        this.factionManager = factionManager;
        this.messageService = messageService;
    }

    @Override
    protected void define() {
        addCondition(commandsManager.getCondition(SenderIsInFactionRequirement.class));
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

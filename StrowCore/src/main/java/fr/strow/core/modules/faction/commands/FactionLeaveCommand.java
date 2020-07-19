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
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class FactionLeaveCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final PermissionsManager permissionsManager;
    private final Messaging messaging;

    @Inject
    public FactionLeaveCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, PermissionsManager permissionsManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("leave")
                .withDescription("Quitter une faction")
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
        addRequirement(commandService.getRequirement(SenderIsNotLeaderRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        Faction faction = factionManager.getFaction(
                strowSender
                        .getProperty(FactionProfile.class)
                        .getProperty(FactionUUID.class)
                        .getFactionUuid()
        );

        strowSender.unregisterProperty(FactionProfile.class);
        permissionsManager.reloadPermissions(strowSender);

        messaging.sendMessage(strowSender, "Vous avez quitté votre faction !");
        messaging.sendMessage(faction, "%s a quitté la faction !", strowSender.getProperty(Nickname.class).getNickname());
    }

    public static class SenderIsNotLeaderRequirement extends Requirement {

        private final SenderIsInFactionRequirement senderIsInFactionRequirement;
        private final PlayerManager playerManager;

        @Inject
        public SenderIsNotLeaderRequirement(SenderIsInFactionRequirement senderIsInFactionRequirement, PlayerManager playerManager) {
            this.senderIsInFactionRequirement = senderIsInFactionRequirement;
            this.playerManager = playerManager;
        }

        @Override
        public List<Condition<CommandSender>> getConditions() {
            List<Condition<CommandSender>> conditions = new ArrayList<>(senderIsInFactionRequirement.getConditions());

            conditions.add(new Condition<CommandSender>() {
                @Override
                public boolean check(CommandSender sender) {
                    StrowPlayer strowSender = playerManager.getPlayer(sender);
                    FactionRole role = strowSender
                            .getProperty(FactionProfile.class)
                            .getProperty(FactionGroup.class)
                            .getRole();

                    return role != FactionRole.LEADER;
                }

                @Override
                public BaseComponent[] getMessage(CommandSender sender) {
                    BaseComponent text = new TextComponent("Pour quitter votre faction, vous devez la dissoudre");
                    text.addExtra("[Je la dissoue]");
                    text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/faction disband"));
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez ici pour dissoudre votre faction").create()));

                    return new BaseComponent[] {text};
                }
            });

            return conditions;
        }
    }
}

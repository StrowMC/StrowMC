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
import fr.strow.core.modules.faction.commands.parameters.MotedMemberParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class FactionPromoteCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;
    private final PermissionsManager permissionsManager;
    private final Messaging messaging;

    @Inject
    public FactionPromoteCommand(CommandService commandService, PlayerManager playerManager, FactionManager factionManager, PermissionsManager permissionsManager, Messaging messaging) {
        super(CommandDescription.builder()
                .withName("promote")
                .withPermission("faction.promote")
                .withDescription("Promouvoir un membre de votre faction")
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

        addParameter(commandService.getParameter(PromotedMemberParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(sender);

        String name = readArg();

        StrowPlayer player = playerManager.getPlayer(name);

        FactionGroup group = player.getProperty(FactionProfile.class).getProperty(FactionGroup.class);
        FactionRole currentRole = group.getRole();

        //noinspection OptionalGetWithoutIsPresent
        FactionRole newRole = FactionRole.getRoleAbove(currentRole).get();
        group.setRole(newRole);

        permissionsManager.reloadPermissions(player);

        Faction faction = factionManager.getFaction(
                player
                        .getProperty(FactionProfile.class)
                        .getProperty(FactionUUID.class)
                        .getFactionUuid()
        );

        messaging.sendMessage(faction, uuid -> !uuid.equals(player.getUniqueId()),"%s a été promu au rang de %s par %s !",
                player.getProperty(Nickname.class).getNickname(),
                newRole.getName(),
                strowSender.getProperty(Nickname.class).getNickname());
    }

    static class PromotedMemberParameter extends Parameter<StrowPlayer> {

        private final MotedMemberParameter motedMemberParameter;
        private final PlayerManager playerManager;
        private final Messaging messaging;

        @Inject
        public PromotedMemberParameter(MotedMemberParameter motedMemberParameter, PlayerManager playerManager, Messaging messaging) {
            super("joueur");

            this.motedMemberParameter = motedMemberParameter;
            this.playerManager = playerManager;
            this.messaging = messaging;
        }

        @Override
        public List<Condition<String>> getConditions(CommandSender sender) {
            List<Condition<String>> conditions = new ArrayList<>(motedMemberParameter.getConditions(sender));

            conditions.add(
                    new Condition<String>() {
                        @Override
                        public boolean check(String arg) {
                            StrowPlayer player = playerManager.getPlayer(arg);

                            FactionRole playerRole = player
                                    .getProperty(FactionProfile.class)
                                    .getProperty(FactionGroup.class)
                                    .getRole();

                            //noinspection OptionalGetWithoutIsPresent
                            return playerRole != FactionRole.getRoleUnder(FactionRole.LEADER).get();
                        }

                        @Override
                        public BaseComponent[] getMessage(String arg) {
                            return messaging.errorMessage("Ce joueur ne peut plus être rétrogradé car il est déjà membre");
                        }
                    }
            );

            return conditions;
        }

        @Override
        public StrowPlayer get(String arg) {
            return playerManager.getPlayer(arg);
        }
    }
}

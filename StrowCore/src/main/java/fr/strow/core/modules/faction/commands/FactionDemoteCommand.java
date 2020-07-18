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
import fr.strow.api.game.faction.player.FactionGroup;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.faction.player.FactionRole;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.core.modules.faction.commands.parameters.MemberFromSenderFactionParameter;
import fr.strow.core.modules.faction.commands.requirements.SenderIsInFactionRequirement;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class FactionDemoteCommand extends EvolvedCommand {

    private final CommandService commandService;
    private final PlayerManager playerManager;

    @Inject
    public FactionDemoteCommand(CommandService commandService, PlayerManager playerManager) {
        super(CommandDescription.builder()
                .withName("demote")
                .withDescription("Dégrader un joueur de votre faction")
                .build());

        this.commandService = commandService;
        this.playerManager = playerManager;

        define();
    }

    @Override
    protected void define() {
        addRequirement(commandService.getRequirement(SenderIsInFactionRequirement.class));

        addParameter(commandService.getParameter(DemotedMemberParameter.class), true);
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer player = playerManager.getPlayer((String) readArg());
        FactionGroup group = player
                .getProperty(FactionProfile.class)
                .getProperty(FactionGroup.class);
        FactionRole currentRole = group.getRole();

        //noinspection OptionalGetWithoutIsPresent
        group.setRole(FactionRole.getRoleById(currentRole.getId() - 1).get());
    }

    static class DemotedMemberParameter extends Parameter<StrowPlayer> {

        private final MemberFromSenderFactionParameter memberFromSenderFactionParameter;
        private final PlayerManager playerManager;

        @Inject
        public DemotedMemberParameter(MemberFromSenderFactionParameter memberFromSenderFactionParameter, PlayerManager playerManager) {
            super("membre");

            this.memberFromSenderFactionParameter = memberFromSenderFactionParameter;
            this.playerManager = playerManager;
        }

        @Override
        public List<Condition<String>> getConditions(CommandSender sender) {
            List<Condition<String>> conditions = memberFromSenderFactionParameter.getConditions(sender);

            conditions.addAll(Arrays.asList(
                    new Condition<String>() {
                        @Override
                        public boolean check(String o) {
                            StrowPlayer player = memberFromSenderFactionParameter.get(o);

                            int playerRole = player
                                    .getProperty(FactionProfile.class)
                                    .getProperty(FactionGroup.class)
                                    .getRole().getId();

                            int senderRole = playerManager.getPlayer(((Player) sender).getUniqueId())
                                    .getProperty(FactionProfile.class)
                                    .getProperty(FactionGroup.class)
                                    .getRole().getId();

                            return senderRole > playerRole;
                        }

                        @Override
                        public String getMessage(String o) {
                            return "Vous ne pouvez pas rétrograder un joueur dont le role est supérieur ou égal au votre";
                        }
                    },
                    new Condition<String>() {
                        @Override
                        public boolean check(String o) {
                            StrowPlayer player = memberFromSenderFactionParameter.get(o);

                            FactionRole playerRole = player
                                    .getProperty(FactionProfile.class)
                                    .getProperty(FactionGroup.class)
                                    .getRole();

                            return playerRole != FactionRole.MEMBER;
                        }

                        @Override
                        public String getMessage(String o) {
                            return "Ce joueur ne peut plus être rétrogradé car il est déjà membre";
                        }
                    }
            ));

            return conditions;
        }

        @Override
        public StrowPlayer get(String arg) {
            return memberFromSenderFactionParameter.get(arg);
        }
    }
}

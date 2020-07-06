/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 16:59
 */

package fr.strow.core.module.faction.commands;

import com.google.inject.Inject;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import fr.strow.core.module.faction.commands.conditions.SenderIsNotInFactionRequirement;
import fr.strow.core.module.faction.commands.parameters.FactionNameParameter;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FactionCreateCommand extends EvolvedCommand {

    private static final Parameter<String> DESCRIPTION = new Parameter<String>("description") {

        private final Pattern PATTERN = Pattern.compile(".{3,254}");

        @Override
        public List<Condition<String>> getConditions() {
            return Collections.singletonList(new Condition<String>() {
                @Override
                public boolean check(String arg) {
                    return PATTERN.matcher(arg).matches();
                }

                @Override
                public String getMessage(String arg) {
                    return "La description de la faction doit comporter au moins de 3 caractères";
                }
            });
        }

        @Override
        public String get(String arg) {
            return arg;
        }
    };

    private final CommandsManager commandsManager;
    private final PlayerManager playerManager;
    private final FactionManager factionManager;

    @Inject
    public FactionCreateCommand(CommandsManager commandsManager, PlayerManager playerManager, FactionManager factionManager) {
        super(CommandDescription.builder()
                .withName("create")
                .withAliases("c")
                .build());

        this.commandsManager = commandsManager;
        this.playerManager = playerManager;
        this.factionManager = factionManager;

        define();
    }

    @Override
    protected void define() {
        addParam(commandsManager.getParameter(FactionNameParameter.class), true);
        addParam(DESCRIPTION, false);

        addCondition(commandsManager.getCondition(SenderIsNotInFactionRequirement.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());
        String name = readArg();

        Optional<String> description = readOptionalArg();

        if (description.isPresent()) {
            factionManager.createFaction(strowSender, name, description.get());
        } else {
            factionManager.createFaction(strowSender, name);
        }
    }
}

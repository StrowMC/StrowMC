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
import fr.strow.core.module.faction.commands.conditions.SenderIsNotInFactionCondition;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.regex.Pattern;

public class FactionCreateCommand extends EvolvedCommand {

    private static final Parameter<String> NAME = new Parameter<>("name") {

        private final Pattern PATTERN = Pattern.compile("[a-zA-Z].{2,254}");

        @Override
        public Optional<String> check(String arg) {
            Optional<String> o = Optional.empty();

            if (PATTERN.matcher(arg).matches()) {
                o = Optional.of(arg);
            }

            return o;
        }

        @Override
        public String getMessage(String arg) {
            return "Le nom de la faction doit commencer par une lettre et doit comporter aumoins 3 caractères";
        }
    };

    private static final Parameter<String> DESCRIPTION = new Parameter<>("name") {

        private final Pattern PATTERN = Pattern.compile(".{3,254}");

        @Override
        public Optional<String> check(String arg) {
            Optional<String> o = Optional.empty();

            if (PATTERN.matcher(arg).matches()) {
                o = Optional.of(arg);
            }

            return o;
        }

        @Override
        public String getMessage(String arg) {
            return "La description de la faction doit comporter au moins de 3 caractères";
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
        addParam(NAME, true);
        addParam(DESCRIPTION, false);

        addCondition(commandsManager.getCondition(SenderIsNotInFactionCondition.class));
    }

    @Override
    protected void execute(CommandSender sender) {
        StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());
        String name = readArg();

        if (factionManager.factionExists(name)) {
            strowSender.sendMessage("Ce nom a déjà été pris par une autre faction");
        } else {
            Optional<String> description = readOptionalArg();

            if (description.isPresent()) {
                factionManager.createFaction(strowSender, name, description.get());
            } else {
                factionManager.createFaction(strowSender, name);
            }
        }
    }
}

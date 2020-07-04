/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:52
 */

package fr.strow.core.module.faction.commands.parameters;

import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Optional;

public class FactionMemberParameter extends Parameter<StrowPlayer> {

    public FactionMemberParameter(String name) {
        super(name);
    }

    @Override
    public Collection<StrowPlayer> tabComplete(CommandSender sender) {
        return null;
    }

    @Override
    public Optional<StrowPlayer> check(String s) {
        return Optional.empty();
    }

    @Override
    public String getMessage(String s) {
        return null;
    }
}

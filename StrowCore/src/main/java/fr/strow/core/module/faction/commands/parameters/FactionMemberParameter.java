/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:52
 */

package fr.strow.core.module.faction.commands.parameters;

import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.*;

public class FactionMemberParameter extends Parameter<StrowPlayer> {

    public FactionMemberParameter(String name) {
        super(name);
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(
                new Condition<>() {
                    @Override
                    public boolean check(String o) {
                        return false;
                    }

                    @Override
                    public String getMessage(String o) {
                        return null;
                    }
                }
        );
    }

    @Override
    public StrowPlayer get(String arg) {
        return null;
    }
}

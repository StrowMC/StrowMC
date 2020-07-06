/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 16:37
 */

package fr.strow.core.utils.commands.conditions;

import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SenderIsPlayerRequirement extends Requirement {

    @Override
    public Condition<CommandSender> getCondition() {
        return new Condition<>() {
            @Override
            public boolean check(CommandSender sender) {
                return sender instanceof Player;
            }

            @Override
            public String getMessage(CommandSender sender) {
                return "Vous devez être un joueur pour exécuter cette commande";
            }
        };
    }
}

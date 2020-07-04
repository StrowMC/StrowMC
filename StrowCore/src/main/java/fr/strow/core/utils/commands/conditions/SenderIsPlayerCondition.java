/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 16:37
 */

package fr.strow.core.utils.commands.conditions;

import me.choukas.commands.api.Condition;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SenderIsPlayerCondition extends Condition {

    @Override
    public boolean check(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public String getMessage() {
        return "Vous devez être un joueur pour exécuter cette commande";
    }
}

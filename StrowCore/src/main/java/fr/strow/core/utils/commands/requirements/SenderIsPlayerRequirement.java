/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 16:37
 */

package fr.strow.core.utils.commands.requirements;

import com.google.inject.Inject;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SenderIsPlayerRequirement extends Requirement {

    @Inject
    public SenderIsPlayerRequirement() {
    }

    @Override
    public List<Condition<CommandSender>> getConditions() {
        return Collections.singletonList(new Condition<CommandSender>() {
            @Override
            public boolean check(CommandSender sender) {
                return sender instanceof Player;
            }

            @Override
            public String getMessage(CommandSender sender) {
                return "Vous devez être un joueur pour exécuter cette commande";
            }
        });
    }
}

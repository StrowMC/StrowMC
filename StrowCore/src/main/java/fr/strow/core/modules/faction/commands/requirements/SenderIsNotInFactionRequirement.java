/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 20:00
 */

package fr.strow.core.modules.faction.commands.requirements;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SenderIsNotInFactionRequirement extends Requirement {

    private final PlayerManager playerManager;

    @Inject
    public SenderIsNotInFactionRequirement(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public List<Condition<CommandSender>> getConditions() {
        return Collections.singletonList(new Condition<CommandSender>() {
            @Override
            public boolean check(CommandSender sender) {
                if (sender instanceof Player) {
                    StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

                    return !strowSender.getOptionalProperty(FactionProfile.class).isPresent();
                } else {
                    return false;
                }
            }

            @Override
            public String getMessage(CommandSender sender) {
                return "Vous devez quitter votre faction pour exécuter cette commande";
            }
        });
    }
}

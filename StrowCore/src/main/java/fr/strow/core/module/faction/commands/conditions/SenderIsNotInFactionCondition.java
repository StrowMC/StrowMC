/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 20:00
 */

package fr.strow.core.module.faction.commands.conditions;

import com.google.inject.Inject;
import fr.strow.api.game.factions.player.FactionProfile;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Condition;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SenderIsNotInFactionCondition extends Condition {

    private final PlayerManager playerManager;

    @Inject
    public SenderIsNotInFactionCondition(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean check(CommandSender sender) {
        if (sender instanceof Player) {
            StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());

            return strowSender.get(FactionProfile.class) == null;
        } else {
            return false;
        }
    }

    @Override
    public String getMessage() {
        return "Vous ne devez appartenir à aucune faction pour exécuter cette commande";
    }
}

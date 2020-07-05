/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:33
 */

package fr.strow.core.module.faction.commands.conditions;

import fr.strow.api.game.factions.player.FactionProfile;
import fr.strow.api.game.factions.player.FactionRole;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Condition;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SenderIsLeaderCondition extends Condition {

    private final PlayerManager playerManager;

    public SenderIsLeaderCondition(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean check(CommandSender sender) {
        if (sender instanceof Player) {
            StrowPlayer strowSender = playerManager.getPlayer(((Player) sender).getUniqueId());
            Optional<FactionProfile> optionalFactionProfile = Optional.ofNullable(strowSender.get(FactionProfile.class));

            return optionalFactionProfile.isPresent() && optionalFactionProfile.get().getFactionGroup().getRole() == FactionRole.LEADER;
        } else {
            return false;
        }
    }

    @Override
    public String getMessage() {
        return "Vous deveez être leader pour exécuter cette commande";
    }
}
/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 17:37
 */

package fr.strow.core.modules.faction.commands.requirements;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.Messaging;
import fr.strow.core.utils.commands.requirements.SenderIsPlayerRequirement;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SenderIsInFactionRequirement extends Requirement {

    private final SenderIsPlayerRequirement senderIsPlayerRequirement;
    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public SenderIsInFactionRequirement(SenderIsPlayerRequirement senderIsPlayerRequirement, PlayerManager playerManager, Messaging messaging) {
        this.senderIsPlayerRequirement = senderIsPlayerRequirement;
        this.playerManager = playerManager;
        this.messaging = messaging;
    }

    @Override
    public List<Condition<CommandSender>> getConditions() {
        List<Condition<CommandSender>> conditions = new ArrayList<>(senderIsPlayerRequirement.getConditions());

        conditions.add(new Condition<CommandSender>() {
            @Override
            public boolean check(CommandSender sender) {
                if (sender instanceof Player) {
                    StrowPlayer strowSender = playerManager.getPlayer(sender.getName());
                    Optional<FactionProfile> optionalFactionProfile = strowSender.getOptionalProperty(FactionProfile.class);

                    return optionalFactionProfile.isPresent();
                } else {
                    return false;
                }
            }

            @Override
            public BaseComponent getMessage(CommandSender sender) {
                return messaging.errorMessage("Vous devez être dans une faction pour exécuter cette commande");
            }
        });

        return conditions;
    }
}

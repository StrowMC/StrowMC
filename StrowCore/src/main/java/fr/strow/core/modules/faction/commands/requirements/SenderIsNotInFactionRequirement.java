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
import fr.strow.api.services.Messaging;
import fr.strow.core.utils.commands.requirements.SenderIsPlayerRequirement;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Requirement;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SenderIsNotInFactionRequirement extends Requirement {

    private final SenderIsPlayerRequirement senderIsPlayerRequirement;
    private final PlayerManager playerManager;
    private final Messaging messaging;

    @Inject
    public SenderIsNotInFactionRequirement(SenderIsPlayerRequirement senderIsPlayerRequirement, PlayerManager playerManager, Messaging messaging) {
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
                StrowPlayer strowSender = playerManager.getPlayer(sender.getName());

                return !strowSender.getOptionalProperty(FactionProfile.class).isPresent();
            }

            @Override
            public BaseComponent[] getMessage(CommandSender sender) {
                return messaging.errorMessage("Vous devez quitter votre faction pour exécuter cette commande");
            }
        });

        return conditions;
    }
}

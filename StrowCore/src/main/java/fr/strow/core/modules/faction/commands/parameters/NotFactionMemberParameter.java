/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:06
 */

package fr.strow.core.modules.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class NotFactionMemberParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;

    @Inject
    public NotFactionMemberParameter(PlayerManager playerManager) {
        super("player");

        this.playerManager = playerManager;
    }

    @Override
    public List<Condition<String>> getConditions(CommandSender sender) {
        return Collections.singletonList(
                new Condition<String>() {
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

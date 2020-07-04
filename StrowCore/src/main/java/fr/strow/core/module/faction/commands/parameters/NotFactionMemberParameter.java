/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:06
 */

package fr.strow.core.module.faction.commands.parameters;

import com.google.inject.Inject;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.game.players.StrowPlayer;
import me.choukas.commands.api.Parameter;

import java.util.Optional;

public class NotFactionMemberParameter extends Parameter<StrowPlayer> {

    private final PlayerManager playerManager;

    @Inject
    public NotFactionMemberParameter(PlayerManager playerManager) {
        super("player");

        this.playerManager = playerManager;
    }

    @Override
    public Optional<StrowPlayer> check(String s) {
        return Optional.empty();
    }

    @Override
    public String getMessage(String s) {
        return null;
    }
}

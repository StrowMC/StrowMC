/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:40
 */

package fr.strow.core.modules.economy.services;

import com.google.inject.Inject;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.services.EconomyService;

import java.util.Comparator;
import java.util.List;

public class EconomyServiceImpl implements EconomyService {

    private final PlayerManager playerManager;

    @Inject
    public EconomyServiceImpl(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public List<StrowPlayer> getRichestPlayers(int n) {
        List<StrowPlayer> players;

        players = playerManager.getPlayers()
                .sorted(Comparator.comparingInt(player ->
                        player.getProperty(Economy.class).getCoins()), n);

        return players;
    }
}

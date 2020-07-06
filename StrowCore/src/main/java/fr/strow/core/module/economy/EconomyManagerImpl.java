/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:40
 */

package fr.strow.core.module.economy;

import com.google.inject.Inject;
import fr.strow.api.game.economy.EconomyManager;
import fr.strow.persistence.dao.EconomyDao;
import me.choukas.commands.utils.Tuple;

import java.util.List;
import java.util.stream.Collectors;

public class EconomyManagerImpl implements EconomyManager {

    private final EconomyDao economyDao;

    @Inject
    public EconomyManagerImpl(EconomyDao economyDao) {
        this.economyDao = economyDao;
    }

    @Override
    public List<Tuple<String, Integer>> getRichestPlayers(int n) {
        List<Tuple<String, Integer>> players;

        players = economyDao.getRichestPlayers(n)
                .stream()
                .map(playerBean ->
                        Tuple.of(playerBean.getName(), playerBean.getCoins()))
                .collect(Collectors.toList());

        return players;
    }
}

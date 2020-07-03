/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:07
 */

package fr.strow.core.module.economy;

import com.google.inject.Inject;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.persistence.beans.EconomyBean;
import fr.strow.persistence.dao.EconomyDao;

import java.util.UUID;

public class EconomyProperty implements AbstractProperty, Economy {

    // DAO
    private final EconomyDao economyDao;

    // Properties
    private int coins;

    @Inject
    public EconomyProperty(EconomyDao economyDao) {
        this.economyDao = economyDao;
    }

    @Override
    public void load(UUID uuid) {
        EconomyBean bean = economyDao.loadEconomy(uuid);
        coins = bean.getCoins();
    }

    @Override
    public void save(UUID uuid) {
        EconomyBean bean = new EconomyBean(uuid, coins);
        economyDao.saveEconomy(bean);
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    @Override
    public void removeCoins(int amount) {
        if (amount > 0) {
            coins -= amount;
        }
    }
}

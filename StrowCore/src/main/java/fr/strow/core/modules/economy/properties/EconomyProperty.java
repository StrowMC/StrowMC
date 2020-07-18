/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:07
 */

package fr.strow.core.modules.economy.properties;

import com.google.inject.Inject;
import fr.strow.api.game.economy.Economy;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.EconomyBean;
import fr.strow.persistence.dao.EconomyDao;

import java.util.UUID;

public class EconomyProperty extends ImplementationProperty implements Economy {

    private final EconomyDao economyDao;

    private UUID uuid;
    private int coins;

    @Inject
    public EconomyProperty(EconomyDao economyDao) {
        this.economyDao = economyDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        EconomyBean bean = economyDao.loadEconomy(uuid);
        coins = bean.getCoins();

        return true;
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

            save(uuid);
        }
    }

    @Override
    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public void removeCoins(int amount) {
        if (amount > 0) {
            coins -= amount;

            save(uuid);
        }
    }
}

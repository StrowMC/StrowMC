/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.factions.player.FactionPower;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.persistence.beans.factions.players.FactionPowerBean;
import fr.strow.persistence.dao.factions.players.FactionPowerDao;

import java.util.UUID;

public class FactionPowerProperty implements FactionPower, AbstractProperty {

    private final FactionPowerDao factionPowerDao;

    private int power;

    @Inject
    public FactionPowerProperty(FactionPowerDao factionPowerDao) {
        this.factionPowerDao = factionPowerDao;
    }

    @Override
    public void load(UUID uuid) {
        FactionPowerBean bean = factionPowerDao.loadPower(uuid);
        power = bean.getPower();
    }

    @Override
    public void save(UUID uuid) {
        FactionPowerBean bean = new FactionPowerBean(uuid, power);
        factionPowerDao.savePower(bean);
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void addPower(int amount) {
        if (amount > 0) {
            power += amount;
        }
    }

    @Override
    public void removePower(int amount) {
        if (amount > 0) {
            power -= amount;
        }
    }
}

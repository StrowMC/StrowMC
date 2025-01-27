/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.modules.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionPower;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.profile.FactionPowerBean;
import fr.strow.persistence.dao.factions.player.FactionPowerDao;

import java.util.UUID;

public class FactionPowerProperty implements FactionPower, ImplementationProperty<FactionPower> {

    private final FactionPowerDao factionPowerDao;

    private int power;

    @Inject
    public FactionPowerProperty(FactionPowerDao factionPowerDao) {
        this.factionPowerDao = factionPowerDao;
    }

    @Override
    public boolean load(UUID uuid) {
        FactionPowerBean bean = factionPowerDao.loadFactionPower(uuid);
        power = bean.getPower();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionPowerBean bean = new FactionPowerBean(uuid, power);
        factionPowerDao.saveFactionPower(bean);
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

    @Override
    public void setPower(int power) {
        if (power > 0) {
            this.power = power;
        }
    }
}

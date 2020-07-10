/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.factions.profile.FactionPower;
import fr.strow.api.properties.ExplicitInitialisedProperty;
import fr.strow.api.properties.ImplicitInitialisedProperty;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.PropertyFactory;
import fr.strow.persistence.beans.factions.profile.FactionPowerBean;
import fr.strow.persistence.dao.factions.profile.FactionPowerDao;

import java.util.UUID;

public class FactionPowerProperty implements PersistentProperty, ImplicitInitialisedProperty, ExplicitInitialisedProperty<FactionPowerProperty.Factory>, FactionPower {

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

    public class Factory extends PropertyFactory {

        public void load(int power) {
            FactionPowerProperty.this.power = power;
        }
    }
}

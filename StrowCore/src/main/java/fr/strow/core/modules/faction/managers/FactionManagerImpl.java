/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 26/06/2020 10:46
 */

package fr.strow.core.modules.faction.managers;

import com.google.inject.Inject;
import fr.strow.api.game.faction.Faction;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.faction.FactionImpl;
import fr.strow.persistence.dao.factions.FactionDao;

import java.util.UUID;

public class FactionManagerImpl implements FactionManager {

    private final PropertiesHandler propertiesHandler;
    private final FactionDao factionDao;

    @Inject
    public FactionManagerImpl(PropertiesHandler propertiesHandler, FactionDao factionDao) {
        this.propertiesHandler = propertiesHandler;
        this.factionDao = factionDao;
    }

    @Override
    public boolean factionExists(UUID uuid) {
        return factionDao.factionExists(uuid);
    }

    @Override
    public boolean factionExists(String name) {
        return factionDao.factionExists(name);
    }

    @Override
    public Faction getFaction(UUID uuid) {
        if (factionDao.factionExists(uuid)) {
            return loadFaction(uuid);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Faction loadFaction(UUID uuid) {
        FactionImpl faction = new FactionImpl(propertiesHandler);
        faction.load(uuid);

        return faction;
    }
}

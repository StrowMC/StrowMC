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
import fr.strow.api.game.faction.FactionsCollection;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.core.modules.faction.FactionImpl;
import fr.strow.persistence.dao.factions.FactionDao;

import java.util.*;

public class FactionManagerImpl implements FactionManager {

    private static final Map<UUID, Faction> factions = new HashMap<>();

    private final PropertiesHandler propertiesHandler;
    private final FactionDao factionDao;

    @Inject
    public FactionManagerImpl(PropertiesHandler propertiesHandler, FactionDao factionDao) {
        this.propertiesHandler = propertiesHandler;
        this.factionDao = factionDao;
    }

    @Override
    public boolean factionExists(UUID uuid) {
        return factions.containsKey(uuid) || factionDao.factionExists(uuid);
    }

    @Override
    public boolean factionExists(String name) {
        try {
            UUID uuid = factionDao.getUUIDFromName(name);

            return factionExists(uuid);
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    @Override
    public Faction loadFaction(UUID uuid) {
        Faction faction;

        if (factions.containsKey(uuid)) {
            faction = factions.get(uuid);
        } else {
            faction = new FactionImpl(propertiesHandler);

            factions.put(uuid, faction);
        }

        ((ImplementationProperty) faction).load(uuid);

        return faction;
    }

    @Override
    public void unloadFactions() {
        for (UUID faction : factions.keySet()) {
            unloadFaction(faction);
        }
    }

    private void unloadFaction(UUID uuid) {
        Faction faction = factions.get(uuid);

        ((ImplementationProperty) faction).save(uuid);
    }

    @Override
    public Faction getFaction(UUID uuid) {
        Faction faction;

        if (factions.containsKey(uuid)) {
            faction = factions.get(uuid);
        } else {
            if (factionDao.factionExists(uuid)) {
                faction = loadFaction(uuid);
            } else {
                throw new IllegalArgumentException();
            }
        }

        return faction;
    }

    @Override
    public Faction getFaction(String name) {
        Optional<Faction> optionalPlayer = getFactions()
                .withName(name)
                .get();

        return optionalPlayer.orElseGet(() -> getFaction(factionDao.getUUIDFromName(name)));
    }

    @Override
    public FactionsCollection getFactions() {
        return new FactionsCollection(new ArrayList<>(factions.values()));
    }
}

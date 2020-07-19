/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:27
 */

package fr.strow.core.modules.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.*;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.api.property.PropertiesGrouping;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.persistence.dao.factions.player.FactionProfileDao;

import java.util.UUID;

public class FactionProfileProperty extends PropertiesGrouping<FactionProfile> implements ImplementationProperty<FactionProfile>, FactionProfile {

    private final FactionProfileDao factionProfileDao;

    @Inject
    public FactionProfileProperty(PropertiesHandler propertiesHandler,
                                  FactionProfileDao factionProfileDao) {
        super(FactionProfile.class, propertiesHandler);

        this.factionProfileDao = factionProfileDao;
    }

    @Override
    public boolean load(UUID uuid) {
        this.uuid = uuid;

        if (factionProfileDao.hasProfile(uuid)) {
            return super.load(uuid);
        } else {
            return false;
        }
    }

    @Override
    public void onRegister(UUID uuid, Factory factory) {
        factionProfileDao.createProfile(uuid, factory.getFactionUuid(), factory.getRole().getId(), factory.getPower(), factory.isClaimer());

        load(uuid);
    }

    @Override
    public void onUnregister(UUID uuid) {
        factionProfileDao.deleteProfile(this.uuid);
    }
}

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
import fr.strow.api.property.PropertiesGrouping;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.persistence.dao.factions.profile.FactionProfileDao;

import java.util.UUID;

public class FactionProfileProperty extends PropertiesGrouping<FactionProfile> implements FactionProfile {

    private final FactionProfileDao factionProfileDao;

    @Inject
    public FactionProfileProperty(PropertiesHandler propertiesHandler,
                                  FactionProfileDao factionProfileDao) {
        super(FactionProfile.class, propertiesHandler);

        this.factionProfileDao = factionProfileDao;
    }

    @Override
    public boolean load(UUID uuid) {
        if (factionProfileDao.hasProfile(uuid)) {
            return super.load(uuid);
        } else {
            return false;
        }
    }

    /*@Override
    public void buildLeaderProfile(UUID factionUuid) {
        getProperty(FactionUUID.class).setFactionUuid(factionUuid);
        getProperty(FactionGroup.class).setRole(FactionRole.LEADER);
        getProperty(FactionPower.class).setPower(10);
        getProperty(FactionClaimer.class).setClaimer(true);
    }

    @Override
    public void buildMemberProfile(UUID factionUuid) {
        getProperty(FactionUUID.class).setFactionUuid(factionUuid);
        getProperty(FactionGroup.class).setRole(FactionRole.MEMBER);
        getProperty(FactionPower.class).setPower(0);
        getProperty(FactionClaimer.class).setClaimer(false);
    }*/
}

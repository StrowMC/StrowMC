/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.factions.profile.FactionClaimer;
import fr.strow.api.properties.FactoringImplementationProperty;
import fr.strow.api.properties.PropertyFactory;
import fr.strow.persistence.beans.factions.profile.FactionClaimerBean;
import fr.strow.persistence.dao.factions.profile.FactionClaimerDao;

import java.util.UUID;

public class FactionClaimerImplementationProperty implements FactoringImplementationProperty<FactionClaimerImplementationProperty.Factory>, PersistentImplementationProperty, FactionClaimer {

    private final FactionClaimerDao factionClaimerDao;

    private boolean claimer;

    @Inject
    public FactionClaimerImplementationProperty(FactionClaimerDao factionClaimerDao) {
        this.factionClaimerDao = factionClaimerDao;
    }

    @Override
    public boolean load(UUID uuid) {
        FactionClaimerBean bean = factionClaimerDao.loadClaimer(uuid);
        claimer = bean.isClaimer();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionClaimerBean bean = new FactionClaimerBean(uuid, claimer);
        factionClaimerDao.saveClaimer(bean);
    }

    @Override
    public boolean isClaimer() {
        return claimer;
    }

    @Override
    public void setClaimer(boolean claimer) {
        this.claimer = claimer;
    }

    public class Factory extends PropertyFactory {

        public void load(boolean claimer) {
           FactionClaimerImplementationProperty.this.claimer = claimer;
        }
    }
}

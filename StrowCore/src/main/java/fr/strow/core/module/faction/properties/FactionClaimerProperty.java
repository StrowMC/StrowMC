/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.factions.player.FactionClaimer;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.persistence.beans.factions.players.FactionClaimerBean;
import fr.strow.persistence.dao.factions.players.FactionClaimerDao;

import java.util.UUID;

public class FactionClaimerProperty implements FactionClaimer, AbstractProperty {

    private final FactionClaimerDao factionClaimerDao;

    private boolean claimer;

    @Inject
    public FactionClaimerProperty(FactionClaimerDao factionClaimerDao) {
        this.factionClaimerDao = factionClaimerDao;
    }

    @Override
    public void load(UUID uuid) {
        FactionClaimerBean bean = factionClaimerDao.loadClaimer(uuid);
        claimer = bean.isClaimer();
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
}

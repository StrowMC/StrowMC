/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.modules.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionUUID;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.profile.FactionUUIDBean;
import fr.strow.persistence.dao.factions.player.FactionUUIDDao;

import java.util.UUID;

public class FactionUUIDProperty implements FactionUUID, ImplementationProperty<FactionUUID> {

    private final FactionUUIDDao factionUUIDDao;

    private UUID factionUUID;

    @Inject
    public FactionUUIDProperty(FactionUUIDDao factionUUIDDao) {
        this.factionUUIDDao = factionUUIDDao;
    }

    @Override
    public boolean load(UUID uuid) {
        FactionUUIDBean bean = factionUUIDDao.loadFactionUuid(uuid);
        factionUUID = bean.getFactionUuid();

        return true;
    }

    @Override
    public void save(UUID uuid) {
        FactionUUIDBean bean = new FactionUUIDBean(uuid, factionUUID);
        factionUUIDDao.saveFactionUuid(bean);
    }

    @Override
    public UUID getFactionUuid() {
        return factionUUID;
    }

    @Override
    public void setFactionUuid(UUID factionUuid) {
        this.factionUUID = factionUuid;
    }
}

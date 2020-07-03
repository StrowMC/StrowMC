/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.factions.player.FactionUUID;
import fr.strow.api.properties.AbstractProperty;
import fr.strow.persistence.beans.factions.players.FactionUUIDBean;
import fr.strow.persistence.dao.factions.players.FactionUUIDDao;

import java.util.UUID;

public class FactionUUIDProperty implements FactionUUID, AbstractProperty {

    private final FactionUUIDDao factionUUIDDao;

    private UUID factionUuid;

    @Inject
    public FactionUUIDProperty(FactionUUIDDao factionUUIDDao) {
        this.factionUUIDDao = factionUUIDDao;
    }

    @Override
    public void load(UUID uuid) {
        FactionUUIDBean bean = factionUUIDDao.loadUuid(uuid);
        factionUuid = bean.getFactionUuid();
    }

    @Override
    public void save(UUID uuid) {
        FactionUUIDBean bean = new FactionUUIDBean(uuid, factionUuid);
        factionUUIDDao.saveUuid(bean);
    }

    @Override
    public UUID getFactionUuid() {
        return factionUuid;
    }
}

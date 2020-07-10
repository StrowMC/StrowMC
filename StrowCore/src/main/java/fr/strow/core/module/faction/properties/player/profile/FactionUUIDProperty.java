/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.factions.profile.FactionUUID;
import fr.strow.api.properties.ExplicitInitialisedProperty;
import fr.strow.api.properties.ImplicitInitialisedProperty;
import fr.strow.api.properties.PersistentProperty;
import fr.strow.api.properties.PropertyFactory;
import fr.strow.persistence.beans.factions.profile.FactionUUIDBean;
import fr.strow.persistence.dao.factions.profile.FactionUUIDDao;

import java.util.UUID;

public class FactionUUIDProperty implements PersistentProperty, ImplicitInitialisedProperty, ExplicitInitialisedProperty<FactionUUIDProperty.Factory>, FactionUUID {

    private final FactionUUIDDao factionUUIDDao;

    private UUID factionUUID;

    @Inject
    public FactionUUIDProperty(FactionUUIDDao factionUUIDDao) {
        this.factionUUIDDao = factionUUIDDao;
    }

    @Override
    public void load(UUID uuid) {
        FactionUUIDBean bean = factionUUIDDao.loadUuid(uuid);
        factionUUID = bean.getFactionUuid();
    }

    @Override
    public void save(UUID uuid) {
        FactionUUIDBean bean = new FactionUUIDBean(uuid, factionUUID);
        factionUUIDDao.saveUuid(bean);
    }

    @Override
    public UUID getFactionUuid() {
        return factionUUID;
    }

    public class Factory extends PropertyFactory {

        public void load(UUID factionUUID) {
            FactionUUIDProperty.this.factionUUID = factionUUID;
        }
    }
}

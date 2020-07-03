/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:27
 */

package fr.strow.core.module.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.factions.player.*;
import fr.strow.api.properties.AbstractProperty;

import java.util.UUID;

public class FactionProfileProperty implements FactionProfile, AbstractProperty {

    private final FactionUUIDProperty factionUUID;
    private final FactionGroupProperty factionGroup;
    private final FactionPowerProperty factionPower;
    private final FactionClaimerProperty factionClaimer;
    private final FactionAutoClaimingProperty factionAutoClaiming;

    @Inject
    public FactionProfileProperty(FactionUUIDProperty factionUUID,
                                  FactionGroupProperty factionGroup,
                                  FactionPowerProperty factionPower,
                                  FactionClaimerProperty factionClaimer,
                                  FactionAutoClaimingProperty factionAutoClaiming) {
        this.factionUUID = factionUUID;
        this.factionGroup = factionGroup;
        this.factionPower = factionPower;
        this.factionClaimer = factionClaimer;
        this.factionAutoClaiming = factionAutoClaiming;
    }


    @Override
    public void load(UUID uuid) {
        factionUUID.load(uuid);
        factionGroup.load(uuid);
        factionPower.load(uuid);
        factionClaimer.load(uuid);
        factionAutoClaiming.load(uuid);
    }

    @Override
    public void save(UUID uuid) {
        factionUUID.save(uuid);
        factionGroup.save(uuid);
        factionPower.save(uuid);
        factionClaimer.save(uuid);
        factionAutoClaiming.save(uuid);
    }

    @Override
    public FactionUUID getFactionUUID() {
        return factionUUID;
    }

    @Override
    public FactionGroup getFactionGroup() {
        return factionGroup;
    }

    @Override
    public FactionPower getFactionPower() {
        return factionPower;
    }

    @Override
    public FactionClaimer getFactionClaimer() {
        return factionClaimer;
    }

    @Override
    public FactionAutoClaiming getFactionAutoClaiming() {
        return factionAutoClaiming;
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.module.faction.properties.player.profile;

import fr.strow.api.game.factions.profile.FactionAutoClaiming;
import fr.strow.api.properties.ImplementationProperty;

public class FactionAutoClaimingImplementationProperty implements ImplementationProperty, FactionAutoClaiming {

    private boolean autoClaim = false;

    @Override
    public boolean isAutoClaiming() {
        return autoClaim;
    }

    @Override
    public void setAutoClaim(boolean autoClaim) {
        this.autoClaim = autoClaim;
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:36
 */

package fr.strow.core.modules.faction.properties.player.profile;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionAutoClaiming;
import fr.strow.api.property.ImplementationProperty;

public class FactionAutoClaimingProperty extends ImplementationProperty implements FactionAutoClaiming {

    private boolean autoClaim = false;

    @Inject
    public FactionAutoClaimingProperty() {

    }

    @Override
    public boolean isAutoClaiming() {
        return autoClaim;
    }

    @Override
    public void setAutoClaim(boolean autoClaim) {
        this.autoClaim = autoClaim;
    }
}

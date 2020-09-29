/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 07:52
 */

package fr.strow.api.game.faction.player;

import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesOwner;
import fr.strow.api.property.PropertyFactory;
import fr.strow.api.property.Registerer;
import fr.strow.api.property.RegistrableProperty;

import java.util.UUID;

public interface FactionProfile extends RegistrableProperty<StrowPlayer, FactionProfile.Factory>, PropertiesOwner<FactionProfile>, Registerer<FactionProfile> {

    class Factory extends PropertyFactory {

        private final UUID factionUuid;
        private final FactionRole role;
        private final int power;
        private final boolean claimer;

        public Factory(UUID factionUuid, FactionRole role, int power, boolean claimer) {
            this.factionUuid = factionUuid;
            this.role = role;
            this.power = power;
            this.claimer = claimer;
        }

        public UUID getFactionUuid() {
            return factionUuid;
        }

        public FactionRole getRole() {
            return role;
        }

        public int getPower() {
            return power;
        }

        public boolean isClaimer() {
            return claimer;
        }
    }
}

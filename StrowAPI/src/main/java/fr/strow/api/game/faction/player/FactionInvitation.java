/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:24
 */

package fr.strow.api.game.faction.player;

import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertyFactory;
import fr.strow.api.property.RegistrableProperty;

import java.util.UUID;

public interface FactionInvitation extends RegistrableProperty<StrowPlayer, FactionInvitation.Factory> {

    UUID getSender();

    UUID getFaction();

    class Factory extends PropertyFactory {

        private final UUID sender;
        private final UUID faction;

        public Factory(UUID sender, UUID faction) {
            this.sender = sender;
            this.faction = faction;
        }

        public UUID getSender() {
            return sender;
        }

        public UUID getFaction() {
            return faction;
        }
    }
}

/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 23/06/2020 08:06
 */

package fr.strow.core.modules.player;

import fr.strow.api.game.player.StrowPlayer;
import fr.strow.api.property.PropertiesGrouping;
import fr.strow.api.property.PropertiesHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class StrowPlayerImpl extends PropertiesGrouping<StrowPlayer> implements StrowPlayer {

    public StrowPlayerImpl(PropertiesHandler propertiesHandler) {
        super(StrowPlayer.class, propertiesHandler);
    }

    @Override
    public Collection<UUID> getRecipients() {
        return Collections.singleton(getUniqueId());
    }
}

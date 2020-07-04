/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 04/07/2020 18:24
 */

package fr.strow.api.game.factions.player;

import fr.strow.api.game.factions.Faction;
import fr.strow.api.game.players.StrowPlayer;

public class FactionInvitation {

    private final StrowPlayer sender;
    private final StrowPlayer target;
    private final Faction faction;

    public FactionInvitation(StrowPlayer sender, StrowPlayer target, Faction faction) {
        this.sender = sender;
        this.target = target;
        this.faction = faction;
    }

    public StrowPlayer getSender() {
        return sender;
    }

    public StrowPlayer getTarget() {
        return target;
    }

    public Faction getFaction() {
        return faction;
    }
}

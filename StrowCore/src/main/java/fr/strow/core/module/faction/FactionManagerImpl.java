/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 26/06/2020 10:46
 */

package fr.strow.core.module.faction;

import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.factions.player.FactionInvitation;
import fr.strow.api.game.players.StrowPlayer;

import java.util.List;

public class FactionManagerImpl implements FactionManager {

    @Override
    public void createFaction(StrowPlayer leader, String name) {

    }

    @Override
    public void createFaction(StrowPlayer leader, String name, String description) {

    }

    @Override
    public boolean factionExists(String name) {
        return false;
    }

    @Override
    public void invitePlayer(StrowPlayer sender, StrowPlayer target) {

    }

    @Override
    public List<FactionInvitation> getPendingInvitations() {
        return null;
    }

    @Override
    public void leaveFaction(StrowPlayer player) {

    }
}

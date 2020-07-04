/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:50
 */

package fr.strow.api.game.factions;

import fr.strow.api.game.factions.player.FactionInvitation;
import fr.strow.api.game.players.StrowPlayer;

import java.util.List;

public interface FactionManager {

    void createFaction(StrowPlayer leader, String name);

    void createFaction(StrowPlayer leader, String name, String description);

    boolean factionExists(String name);

    void invitePlayer(StrowPlayer sender, StrowPlayer target);

    List<FactionInvitation> getPendingInvitations();

    void leaveFaction(StrowPlayer player);
}

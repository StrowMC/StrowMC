package fr.strow.api.services;

import fr.strow.api.game.faction.player.FactionInvitation;
import fr.strow.api.game.player.StrowPlayer;

import java.util.List;
import java.util.UUID;

public interface FactionService {

    void createFaction(StrowPlayer leader, String name);

    void createFaction(StrowPlayer leader, String name, String description);

    boolean factionExists(String name);

    boolean factionExists(UUID uuid);

    List<FactionInvitation> getPendingInvitations();

    void invitePlayer(StrowPlayer sender, StrowPlayer target);

    void leaveFaction(StrowPlayer player);
}

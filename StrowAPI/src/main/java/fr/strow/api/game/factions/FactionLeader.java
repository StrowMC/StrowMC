package fr.strow.api.game.factions;

import java.util.UUID;

public interface FactionLeader {

    /**
     * Get the faction leader
     *
     * @return uuid of the faction leader
     */
    UUID getLeader();

    /**
     * Change the faction leader
     *
     * @param uuid uuid of the new leader
     */
    void setLeader(UUID uuid);
}

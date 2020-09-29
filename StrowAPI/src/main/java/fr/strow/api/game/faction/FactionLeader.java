package fr.strow.api.game.faction;

import fr.strow.api.property.Property;

import java.util.UUID;

public interface FactionLeader extends Property<Faction> {

    /**
     * Get the faction leader
     *
     * @return uuid of the faction leader
     */
    UUID getLeader();

    /**
     * Change the faction leader
     *
     * @param leader uuid of the new leader
     */
    void setLeader(UUID leader);
}

package fr.strow.api.game.factions;

import fr.strow.api.game.Property;

public interface FactionName extends Property {

    /**
     * Get the faction name
     *
     * @return name of the faction
     */
    String getName();

    /**
     * Change the faction name
     *
     * @param name new name
     */
    void setName(String name);
}

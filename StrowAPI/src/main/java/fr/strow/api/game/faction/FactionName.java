package fr.strow.api.game.faction;

import fr.strow.api.game.Property;

public interface FactionName extends Property<Faction> {

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

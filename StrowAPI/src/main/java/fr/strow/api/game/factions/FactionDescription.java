package fr.strow.api.game.factions;

import fr.strow.api.game.Property;

public interface FactionDescription extends Property {

    /**
     * Get the faction description
     *
     * @return description of the faction
     */
    String getDescription();

    /**
     * Change the faction description
     *
     * @param description new description
     */
    void setDescription(String description);
}

package fr.strow.api.game.faction;

import fr.strow.api.property.Property;

public interface FactionPoints extends Property<Faction> {

    /**
     * Get the faction points
     *
     * @return points of the faction
     */
    int getPoints();

    /**
     * Add points to the faction points
     *
     * @param points points to add
     */
    void addPoints(int points);

    /**
     * Remove points from the faction points
     *
     * @param points points to remove
     */
    void removePoints(int points);
}

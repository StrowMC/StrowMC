package fr.strow.api.game.factions;

public interface FactionPoints {

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

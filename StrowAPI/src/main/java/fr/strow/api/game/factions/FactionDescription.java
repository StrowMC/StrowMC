package fr.strow.api.game.factions;

public interface FactionDescription {

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

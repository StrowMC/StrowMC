package fr.strow.api.game.faction;

import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.RegistrableProperty;

public interface FactionDescription extends RegistrableProperty<Faction, EmptyPropertyFactory> {

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

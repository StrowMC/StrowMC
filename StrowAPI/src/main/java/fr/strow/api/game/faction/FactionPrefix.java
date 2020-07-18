package fr.strow.api.game.faction;

import fr.strow.api.game.Property;

public interface FactionPrefix extends Property<Faction> {

    String getPrefix();

    void setPrefix(String prefix);
}

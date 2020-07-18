package fr.strow.api.game.faction;

import fr.strow.api.game.Property;
import org.bukkit.Location;

public interface FactionHome extends Property<Faction> {

    Location getHome();

    void setHome(Location home);
}

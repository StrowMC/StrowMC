package fr.strow.api.game.faction;

import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.RegistrableProperty;
import org.bukkit.Location;

public interface FactionHome extends RegistrableProperty<Faction, EmptyPropertyFactory> {

    Location getHome();

    void setHome(Location home);
}
